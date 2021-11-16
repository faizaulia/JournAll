package com.example.journall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class OwnedList: AppCompatActivity() {
  private lateinit var dbref : DatabaseReference
  private lateinit var jurnalRecycleView : RecyclerView
  private lateinit var jurnalArrayList : ArrayList<Jurnal>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.profile)

    jurnalRecycleView = findViewById(R.id.ownedList)!!
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
//            Log.d("TAG", "TOLONG ISI ${user}")
            if (user != null && user.id_pengguna == 1) { //ntar cek ama id user disini, kalau sama tambahin
              jurnalArrayList.add(user!!)
            }
          }
          var adapter = JurnalAdapter(jurnalArrayList)
          jurnalRecycleView.adapter = adapter
          adapter.setOnItemClickListener(object : JurnalAdapter.onItemClickListener{
            override fun onItemClicked(position: Int) {
              val intent = Intent(this@OwnedList, DetailJurnalActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
              intent.putExtra("position", position)
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