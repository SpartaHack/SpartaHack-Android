package com.spartahack.spartahack_android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.spartahack.spartahack_android.tools.Timer
import kotlinx.android.synthetic.main.app_bar_main.*
import java.lang.System.currentTimeMillis

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Create the events notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_ID = getString(R.string.events_id)
            val name = getString(R.string.events_name)
            val descriptionText = getString(R.string.events_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val eventsChannel = NotificationChannel(CHANNEL_ID, name, importance)
            eventsChannel.description = descriptionText
            // Register the channel with the system.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(eventsChannel)
        }

        val END_DATE = 1580468400000 // Should be the date of the event in milliseconds
        val currentDate = currentTimeMillis()
        val endTimeMills = END_DATE - currentDate
        val timer = Timer(endTimeMills, 1000, countdownTimer)

        timer.start()
    }

    override fun onBackPressed() {
            super.onBackPressed()
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
        return true
    }
}
