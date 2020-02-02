package com.spartahack.spartahack_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0

private val auth0: Auth0? = null

class LoginMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginmain_activity)
        val loginButton: Button = findViewById<Button>(R.id.logout)
        loginButton.setOnClickListener { logout() }
        //Obtain the token from the Intent's extras
        val accessToken: String? = getIntent().getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN)
        val textView: TextView = findViewById<TextView>(R.id.credentials)
        textView.text = accessToken
    }

    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(LoginActivity.EXTRA_CLEAR_CREDENTIALS, true)
        startActivity(intent)
        finish()
    }
}