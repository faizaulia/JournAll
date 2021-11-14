package com.example.journall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class InputJurnalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_jurnal)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        returnBtn.setOnClickListener {
            finish()
        }
    }
}