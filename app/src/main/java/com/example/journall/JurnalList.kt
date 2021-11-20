package com.example.journall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
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

    val searchButtons = findViewById<SearchView>(R.id.searchBar)

    searchButtons.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(p0: String?): Boolean {
        //Performs search when user hit the search button on the keyboard
        return false
      }
      override fun onQueryTextChange(p0: String?): Boolean {
        //Start filtering the list as user start entering the characters
        Log.d("TAG", "${p0}")
        jurnalArrayList.clear()
        getJurnalData(p0)
        return false
      }
    })
  }

  private fun getJurnalData(keyword: String ?= null) {
    dbref = FirebaseDatabase.getInstance().getReference("Jurnal")
    dbref.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()){
          for (userSnapshot in snapshot.children){
            val user = userSnapshot.getValue(Jurnal::class.java)
            user!!.key = userSnapshot.key
            if (keyword != null) {
              val check: Boolean = user.judul!!.contains(keyword!!, ignoreCase = true)
              if (check) {
                jurnalArrayList.add(user!!)
              }
            }
            else {
              jurnalArrayList.add(user!!)
            }
          }
          var adapter = JurnalAdapter(jurnalArrayList)
          adapter.setOnItemClickListener(object : JurnalAdapter.onItemClickListener{
            override fun onItemClicked(key: String?, position: Int) {
              val intent = Intent(this@JurnalList, DetailJurnalActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
              intent.putExtra("key", key)
              startActivity(intent)
            }
          })
          jurnalRecycleView.adapter = adapter
        }
      }
      override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
      }
    })
  }
}