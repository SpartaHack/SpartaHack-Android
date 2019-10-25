package com.spartahack.spartahack_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.view.*
import com.spartahack.spartahack_android.tools.APICall

class LoginActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "This will be the profile view", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    fun login(view:View){
        /* This function is called when the "Login" button is pressed in the view. */
        val username = view.username.text.toString()
        val password = view.password.text.toString()

        var text = ""
        val duration = Toast.LENGTH_SHORT

        if(username != "" && password != ""){
            APICall("login")
        }
        else{
            text = "Username and password are required."
            val noInputToast = Toast.makeText(applicationContext, text, duration)
            noInputToast.show()
        }

    }

    fun signUp(view:View){
        // DON'T FORGET TO SEND USER DIRECTLY TO THE REGISTRATION PAGE!!!!!!!
        val signUpIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http;//www.19.spartahack.com"))
        startActivity(signUpIntent)
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
