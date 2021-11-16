package com.example.journall

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatViewInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class JurnalList: AppCompatActivity() {
  private lateinit var dbref : DatabaseReference
  private lateinit var jurnalRecycleView : RecyclerView
  private lateinit var jurnalArrayList : ArrayList<Jurnal>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.cari_jurnal)

    val returnBtn = findViewById<ImageButton>(R.id.returnButton)
    returnBtn.setOnClickListener {
      var i = Intent(this@JurnalList,MainActivity::class.java)
      startActivity(i)
      finish()
    }

    jurnalRecycleView = findViewById(R.id.searchList)!!
    jurnalRecycleView.layoutManager = LinearLayoutManager(this)
    jurnalRecycleView.setHasFixedSize(true)

    jurnalArrayList = arrayListOf<Jurnal>()
    getJurnalData()
  }

  private fun getJurnalData() {
    dbref = FirebaseDatabase.getInstance().getReference("Jurnal")
    dbref.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()){
          for (userSnapshot in snapshot.children){
            val user = userSnapshot.getValue(Jurnal::class.java)
            user!!.key = userSnapshot.key
            jurnalArrayList.add(user!!)
          }
          var adapter = JurnalAdapter(jurnalArrayList)
          jurnalRecycleView.adapter = adapter
          adapter.setOnItemClickListener(object : JurnalAdapter.onItemClickListener{
            override fun onItemClicked(position: Int) {
              val intent = Intent(this@JurnalList, DetailJurnalActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
              intent.putExtra("key", position)
              startActivity(intent)
            }

          })
        }
      }
      override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
      }
    })

  }
}