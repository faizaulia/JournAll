package com.example.journall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatViewInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class JurnalList: AppCompatActivity() {
  private lateinit var dbref : DatabaseReference
  private lateinit var jurnalRecycleView : RecyclerView
  private lateinit var jurnalArrayList : ArrayList<Jurnal>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.cari_jurnal)

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
            jurnalArrayList.add(user!!)
          }
          jurnalRecycleView.adapter = JurnalAdapter(jurnalArrayList)
        }
      }
      override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
      }
    })

  }
}