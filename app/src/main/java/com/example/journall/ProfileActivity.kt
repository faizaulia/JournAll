package com.example.journall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        val returnBtn = findViewById<ImageButton>(R.id.returnBtn)
        returnBtn.setOnClickListener {
            finish()
        }
    }
}