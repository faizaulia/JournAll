package com.example.journall

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.database.FirebaseDatabase

class InputJurnalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_jurnal)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        val submitBtn = findViewById<Button>(R.id.submitbtn)

        val db = FirebaseDatabase.getInstance()
        val databaseReference = db.getReference("Jurnal")

        returnBtn.setOnClickListener {
            finish()
        }
        submitBtn.setOnClickListener {
            val judul = findViewById<EditText>(R.id.inputJudul).getText().toString()
            val penulis = findViewById<EditText>(R.id.inputPenulis).getText().toString()
            val tahun = findViewById<EditText>(R.id.inputTahun).getText().toString()
            val abstrak = findViewById<EditText>(R.id.inputAbstrak).getText().toString()

            if ((judul.length < 1) || (penulis.length < 1) || (tahun.length < 1) || (abstrak.length < 1)) { // INI KALAU ADA YANG NULL BATALIN
                Log.d("TAG", "TOLONG ISI SELURUH KOLOMNYA")
            }
            else {
                var key = databaseReference.push().key
                var keyString = key.toString()
                var model = JurnalModel(judul, penulis, tahun, abstrak, keyString, 1)

                databaseReference.child(key!!).setValue(model).addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "Jurnal Berhasil Ditambahkan")
                    }
                }
            }
        }
    }
}