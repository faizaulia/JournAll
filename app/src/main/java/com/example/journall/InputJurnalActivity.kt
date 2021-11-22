package com.example.journall

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InputJurnalActivity : AppCompatActivity() {
    private var PICK_IMAGE_REQUEST = 12
    private var imagePath : Uri? = null
    val PDF: Int = 0
    lateinit var uri: Uri
    lateinit var mStorage: StorageReference
    lateinit var urlDownload : String

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if(requestCode == PDF) {
                uri = data!!.data!!
                val filename = UUID.randomUUID().toString()
                val ref = FirebaseStorage.getInstance().getReference("/uploads/${filename}.pdf")

                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                ref.putFile(uri).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        urlDownload = it.toString()
                        progressDialog.dismiss()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, "Fail upload", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_jurnal)
        val returnBtn = findViewById<ImageButton>(R.id.returnButton)
        val submitBtn = findViewById<Button>(R.id.submitbtn)
        val uploadBtn = findViewById<ImageButton>(R.id.uploadBtn)


        uploadBtn.setOnClickListener(View.OnClickListener {
                view: View? -> val intent = Intent()
            intent.type="application/pdf"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF)
        })

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

            if ((judul.length < 1) || (penulis.length < 1) || (tahun.length < 1) || (abstrak.length < 1) || (urlDownload == null)) { // INI KALAU ADA YANG NULL BATALIN
                Toast.makeText(this, "Please fill all field!", Toast.LENGTH_SHORT).show()
            }
            else {
                var key = databaseReference.push().key
                var keyString = key.toString()
                var id_pengguna = Firebase.auth.currentUser!!.uid.toString()
                var model = JurnalModel(judul, penulis, tahun, abstrak, keyString, id_pengguna, urlDownload)

                databaseReference.child(key!!).setValue(model).addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Jurnal Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                        intent = Intent(this@InputJurnalActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}