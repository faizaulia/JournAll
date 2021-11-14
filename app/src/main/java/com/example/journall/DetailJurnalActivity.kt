package com.example.journall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat

class DetailJurnalActivity : AppCompatActivity() {
    //TODO : Get login status
    private var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_jurnal)
        val dwnldBtn = findViewById<Button>(R.id.dwnldbtn)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        if(isLogin){
            val newColor = ContextCompat.getColorStateList(this@DetailJurnalActivity,R.color.primary)
            dwnldBtn.isEnabled = true
            dwnldBtn.backgroundTintList = newColor
        }
        returnBtn.setOnClickListener {
            finish()
        }
    }
}