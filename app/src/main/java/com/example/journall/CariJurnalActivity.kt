package com.example.journall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class CariJurnalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cari_jurnal)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        returnBtn.setOnClickListener {
            finish()
        }
    }
}