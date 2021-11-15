package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class CariJurnalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cari_jurnal)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        returnBtn.setOnClickListener {
            var i = Intent(this,JurnalList::class.java)
            startActivity(i)
        }
    }
}