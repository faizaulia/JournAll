package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat

class DetailJurnalActivity : AppCompatActivity() {
    //TODO : Get login status
    private var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_jurnal)
        findViewById<TextView>(R.id.judulDetail).setText(intent.getStringExtra("key"))
        val dwnldBtn = findViewById<Button>(R.id.dwnldbtn)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        if(isLogin){
            val newColor = ContextCompat.getColorStateList(this@DetailJurnalActivity,R.color.primary)
            dwnldBtn.isEnabled = true
            dwnldBtn.backgroundTintList = newColor
        }
        returnBtn.setOnClickListener {
            var i = Intent(this,JurnalList::class.java)
            startActivity(i)
            finish()
        }
    }
}