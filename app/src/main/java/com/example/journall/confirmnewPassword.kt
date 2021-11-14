package com.example.journall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class confirmnewPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmnew_password)
        val returnBtncp = findViewById<ImageButton>(R.id.btn_returncp)
        returnBtncp.setOnClickListener {
            finish()
        }
    }
}