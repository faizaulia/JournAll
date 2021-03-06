package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.net.Uri


class DetailJurnalActivity : AppCompatActivity() {
    //TODO : Get login status
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_jurnal)
        val key = intent.getStringExtra("key")
        val dwnldBtn = findViewById<Button>(R.id.dwnldbtn)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        returnBtn.setOnClickListener {
            var i = Intent(this,JurnalList::class.java)
            startActivity(i)
            finish()
        }
        val dbref = FirebaseDatabase.getInstance().getReference("Jurnal").child(key!!)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val jurnal = snapshot.getValue(Jurnal::class.java)
                    findViewById<TextView>(R.id.judulJurnal).setText(jurnal!!.judul)
                    findViewById<TextView>(R.id.penulisTahunJurnal).setText(jurnal!!.penulis + "\n" + jurnal!!.tahun)
                    findViewById<TextView>(R.id.abstraksiJurnal).setText(jurnal!!.abstrak)
                    findViewById<Button>(R.id.dwnldbtn).setOnClickListener {
                      val intent = Intent()
                      intent.action = Intent.ACTION_VIEW
                      intent.addCategory(Intent.CATEGORY_BROWSABLE)
                      intent.data = Uri.parse(jurnal!!.downloadURL)
                      startActivity(intent)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}