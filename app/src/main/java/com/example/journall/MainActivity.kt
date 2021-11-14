package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //TODO : Get name of guest to change, Get login status
    private var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val profileButton = findViewById<ImageButton>(R.id.profileButton)
        val cariButton = findViewById<ImageButton>(R.id.cariButton)
        val uploadButton = findViewById<ImageButton>(R.id.uploadButton)
        val logoutButton = findViewById<ImageButton>(R.id.logoutButton)
        val login = findViewById<TextView>(R.id.loginButton)
        val user = Firebase.auth.currentUser
        if(user != null){
            val username = user.displayName
            val newColor = ContextCompat.getColorStateList(this@MainActivity,R.color.primary)

            findViewById<TextView>(R.id.username).text = username
            findViewById<TextView>(R.id.loginButton).visibility = View.GONE

            profileButton.isEnabled = true
            profileButton.backgroundTintList = newColor
            cariButton.isEnabled = true
            cariButton.backgroundTintList = newColor
            uploadButton.isEnabled = true
            uploadButton.backgroundTintList = newColor
            logoutButton.isEnabled = true
            logoutButton.backgroundTintList = newColor

        }
        login.setOnClickListener{
            intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)

        }

        profileButton.setOnClickListener {
            intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        cariButton.setOnClickListener {
            intent = Intent(this@MainActivity, CariJurnalActivity::class.java)
            startActivity(intent)
        }
        uploadButton.setOnClickListener {
            intent = Intent(this@MainActivity, InputJurnalActivity::class.java)
            startActivity(intent)
        }
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
            finish()
        }

    }

}