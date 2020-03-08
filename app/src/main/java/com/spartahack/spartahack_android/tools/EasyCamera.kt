package com.spartahack.spartahack_android.tools

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.view.SurfaceHolder
import com.spartahack.spartahack_android.QRScannerActivity

/* Hi. I am aware that the Camera API is deprecated. However, it is a known problem in Android that
* it is extremely difficult to work with the camera. I have researched alternatives, but the
* original Camera API is the most stable, and easiest to implement in an understandable manner.
* Additionally, the ML Kit used to read the barcodes requires metadata about the camera that is not
* provided by other libraries and APIs. In future versions of the app, this should be updated to a
* more recent library or API. */


@Suppress("DEPRECATION")
@SuppressWarnings("deprecation")
class EasyCamera(holder:SurfaceHolder) {

    private val context = QRScannerActivity()
    var camera : Camera? = null
    val id = 0

    init{
        if(checkCameraHardware(context)){
            camera = getCameraInstance()
        }
    }


    private fun checkCameraHardware(context: Context): Boolean {
        /* Make sure device has a camera. */
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    private fun getCameraInstance(): Camera? {
        return try {
            Camera.open(id) // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }
}