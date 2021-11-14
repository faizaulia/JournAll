package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class forgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val returnBtnforgot = findViewById<ImageButton>(R.id.btn_returnfg)
        returnBtnforgot.setOnClickListener {
            finish()
        }

        val confirmpass = findViewById<Button>(R.id.btn_submit)
        confirmpass.setOnClickListener{
            intent = Intent(this@forgotPassword, confirmnewPassword::class.java)
            startActivity(intent)
        }



    }
}