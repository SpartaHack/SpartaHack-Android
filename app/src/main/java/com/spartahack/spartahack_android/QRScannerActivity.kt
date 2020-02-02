package com.spartahack.spartahack_android

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.spartahack.spartahack_android.tools.CameraPreview
import com.spartahack.spartahack_android.tools.ImageAnalyzer
import kotlinx.android.synthetic.main.qr_view.*

/* Hi. I am aware that the Camera API is deprecated. However, it is a known problem in Android that
* it is extremely difficult to work with the camera. I have researched alternatives, but the
* original Camera API is the most stable, and easiest to implement in an understandable manner.
* Additionally, the ML Kit used to read the barcodes requires metadata about the camera that is not
* provided by other libraries and APIs. In future versions of the app, this should be updated to a
* more recent library or API. */

@Suppress("DEPRECATION")
@SuppressWarnings("deprecation")
class QRScannerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val previewView = camera_preview
    private val cameraButton = photoButton
    private var qrString : String? = null
    private var cameraPreview : CameraPreview? = null
    private var camera : Camera? = null
    private val cameraID = 0
    private lateinit var pictureData : ByteArray
    private val picture = Camera.PictureCallback { data, _ ->
            pictureData = data
            return@PictureCallback
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)


        // Initializes the camera and preview.
        initCamera()
        initPreview()

        /* When the user clicks the camera button, a picture is taken.
        * Additionally, all of the QR processing is done as well, since it guarantees that there is
        * picture data to work with. */
        cameraButton.setOnClickListener {
            // get an image from the camera
            camera?.takePicture(null, null, picture)
            // Stops the preview after taking a picture.
            camera?.stopPreview()
            // Release the camera so that it can be used by other applications.
            camera?.release()

            // Get the string represented by the QR code.
            qrString = processImage(pictureData, this)
        }


    }


    private fun checkCameraHardware(context: Context): Boolean {
        /* Make sure device has a camera. */
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    private fun getCameraInstance(): Camera? {
        /* Safely gets an instance of the Camera. */
        return try {
            Camera.open(cameraID) // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }

    private fun initCamera(){
        /* Takes care of all of the processes needed to start the camera. */

        // Initializes the camera safely.
        if(checkCameraHardware(this)){
            camera = getCameraInstance()
        }

        // Sets the camera orientation.
        camera?.setDisplayOrientation(90)
    }

    private fun initPreview(){
        /* Takes care of all of the processes needed to create a preview and associate it with the
        * camera. */

        // Creates an instance of the CameraPreview class.
        cameraPreview = camera?.let{CameraPreview(this, it)}

        // Initializes the preview.
        cameraPreview?.also {
            val preview: FrameLayout = previewView
            preview.addView(it)
        }

        // Checks if the preview was able to be initialized. If not, a Toast is created to tell
        // the user to exit the application and retry.
        if(cameraPreview == null){
            val message = "Camera preview was unable to be initialized. Please close the application" +
                    " and try again."
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        }

        // Starts the preview for the camera.
        camera?.startPreview()
    }


    private fun processImage(imgData:ByteArray, context: Context) : String?{

        // Set options for the detector.
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()

        // Create an ImageAnalyzer and use it to get the rotation of the camera.
        val imageAnalyzer = ImageAnalyzer()
        val rotation = imageAnalyzer.getRotation(cameraID.toString(), QRScannerActivity(), context)

        // Set metadata for the detector.
        val metadata = FirebaseVisionImageMetadata.Builder()
            .setWidth(480) // 480x360 is typically sufficient for
            .setHeight(360) // image recognition
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .setRotation(rotation)
            .build()

        // Create an image compatible with Firebase's Barcode Detector.
        val image = FirebaseVisionImage.fromByteArray(imgData, metadata)

        // Create the detector.
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        var returnValue : String? = null

        val result = detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                // Task completed successfully
                val barcode = barcodes[0]

                // QR code should only ever be a TYPE_TEXT.
                if (barcode.valueType == FirebaseVisionBarcode.TYPE_TEXT){
                    returnValue = barcode.rawValue
                    Toast.makeText(context, "QR Value: " + returnValue, Toast.LENGTH_SHORT).show()

                }else{
                    Log.d("FirebaseVision", "Barcode returned non-text type.")
                    val message = "QR code read incorrectly. Please close and try again."
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener {
                // Task failed with an exception
                Log.d("FirebaseVision", it.toString())
                val message = "QR code read incorrectly. Please close and try again."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

        return returnValue
    }

    override fun onResume() {
        // Initializes the camera and preview.
        initCamera()
        initPreview()
        super.onResume()
    }

    override fun onPause() {
        // Stop the camera preview.
        camera?.stopPreview()
        // Release the camera so that it can be used by other applications.
        camera?.release()
        super.onPause()
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // do nothing. already here

            }
            R.id.nav_maps -> {
                // set activity to maps

                var intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_faq -> {
                // set activity to faq
                var intent = Intent(this, FAQActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_schedule -> {
                // set activity to schedule
                var intent = Intent(this, ScheduleActivity::class.java)
                startActivity(intent)
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
