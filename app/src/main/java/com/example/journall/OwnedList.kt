package com.example.journall

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class OwnedList: AppCompatActivity() {
  private lateinit var dbref : DatabaseReference
  private lateinit var jurnalRecycleView : RecyclerView
  private lateinit var jurnalArrayList : ArrayList<Jurnal>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.profile)

    val returnBtn = findViewById<ImageButton>(R.id.returnBtn)
    val user = Firebase.auth.currentUser
    findViewById<TextView>(R.id.nama).text = user!!.displayName
    findViewById<TextView>(R.id.email).text = user!!.email
    returnBtn.setOnClickListener {
      var i = Intent(this@OwnedList,MainActivity::class.java)
      startActivity(i)
      finish()
    }

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
          for (jurnalSnapshot in snapshot.children){
            val jurnal = jurnalSnapshot.getValue(Jurnal::class.java)
            if (jurnal != null && jurnal.id_pengguna == Firebase.auth.currentUser!!.uid) { //ntar cek ama id user disini, kalau sama tambahin
              jurnalArrayList.add(jurnal!!)
            }
          }
          var adapter = OwnedAdapter(jurnalArrayList)
          jurnalRecycleView.adapter = adapter
          adapter.setOnItemClickListener(object : OwnedAdapter.onItemClickListener{
            override fun onItemClicked(position: Int) {
              intent = Intent(this@OwnedList, OwnedList::class.java)
              startActivity(intent)
              finish()
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