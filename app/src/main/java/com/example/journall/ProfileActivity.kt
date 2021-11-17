package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        val returnBtn = findViewById<ImageButton>(R.id.returnBtn)
        val user = Firebase.auth.currentUser
        findViewById<TextView>(R.id.nama).text = user!!.displayName
        findViewById<TextView>(R.id.email).text = user!!.email
        returnBtn.setOnClickListener {
            finish()
        }

        var i = Intent(this@ProfileActivity,OwnedList::class.java)
        startActivity(i)
    }
}