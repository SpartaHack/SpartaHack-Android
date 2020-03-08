package com.spartahack.spartahack_android

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.*
import kotlinx.android.synthetic.main.faq_view.*
import com.spartahack.spartahack_android.scripts.faqMainSuspend


class FAQActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faq_view)

        // Makes the API call and sends the data to the activity.
        val displayString = GlobalScope.async { faqMainSuspend() }

        runBlocking { faqTextView.text = HtmlCompat.fromHtml(displayString.await(), 0) }
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
                // set activity to home
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_faq -> {
                // already here
            }
        }

        return true
    }
}
