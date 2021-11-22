package com.example.journall

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage

import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList


class OwnedList: AppCompatActivity() {
  private lateinit var dbref : DatabaseReference
  private lateinit var jurnalRecycleView : RecyclerView
  private lateinit var jurnalArrayList : ArrayList<Jurnal>
  private lateinit var avatar : ImageView
  lateinit var urlDownload : String
  lateinit var imageUri : Uri

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.profile)
    val returnBtn = findViewById<ImageButton>(R.id.returnBtn)
    val user = Firebase.auth.currentUser
    val ava = findViewById<ImageView>(R.id.avatar)
//    val bmp = BitmapFactory.decodeStream(contentResolver.openInputStream(user?.photoUrl!!))
//    Picasso.get().load(bmp).into(ava)
//    findViewById<ImageView>(R.id.avatar).setImageBitmap(bmp)
    Glide.with(this).load(user!!.photoUrl).centerCrop().into(ava)
    findViewById<TextView>(R.id.nama).text = user!!.displayName
    findViewById<TextView>(R.id.email).text = user!!.email
    returnBtn.setOnClickListener {
      var i = Intent(this@OwnedList, MainActivity::class.java)
      startActivity(i)
      finish()
    }

    jurnalRecycleView = findViewById(R.id.ownedList)!!
    jurnalRecycleView.layoutManager = LinearLayoutManager(this)
    jurnalRecycleView.setHasFixedSize(true)

    jurnalArrayList = arrayListOf<Jurnal>()
    getJurnalData()

    val card = findViewById<CardView>(R.id.cardView)
    avatar = findViewById<ImageView>(R.id.avatar)


    card.setOnClickListener {
      val openGalleryIntent = Intent()
      openGalleryIntent.type = "image/*"
      openGalleryIntent.action = Intent.ACTION_GET_CONTENT
      startActivityForResult(openGalleryIntent, 1000)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(requestCode == 1000){
      if(resultCode == Activity.RESULT_OK){
        imageUri = data?.data!!
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("avatar/${filename}")

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        ref.putFile(imageUri).addOnSuccessListener {
          ref.downloadUrl.addOnSuccessListener {
//            urlDownload = it.toString()
            val user = Firebase.auth.currentUser
            val profileUpdates = userProfileChangeRequest {
              photoUri = it
            }

            user!!.updateProfile(profileUpdates)
              .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                  Toast.makeText(this, "Profile updated!", Toast.LENGTH_LONG).show()
                  intent = Intent(this@OwnedList, OwnedList::class.java)
                  startActivity(intent)
                  finish()
                }
              }
            progressDialog.dismiss()
          }
        }.addOnFailureListener{
          Toast.makeText(this, "Fail upload", Toast.LENGTH_LONG).show()
        }
      }
    }
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