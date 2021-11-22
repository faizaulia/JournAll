package com.example.journall

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val returnBtnregis = findViewById<ImageButton>(R.id.btn_return)
        val username = findViewById<EditText>(R.id.ed_username)
        val email = findViewById<EditText>(R.id.ed_email)
        val password = findViewById<EditText>(R.id.ed_passregis)
        val cpassword = findViewById<EditText>(R.id.ed_cmpass)
        val question = findViewById<EditText>(R.id.ed_squestion)
        val answer = findViewById<EditText>(R.id.ed_sanswer)
        val registerBtn = findViewById<Button>(R.id.btn_register)

        returnBtnregis.setOnClickListener {
            finish()
        }

        registerBtn.setOnClickListener{
            if (TextUtils.isEmpty(username.text.toString()) ||
                TextUtils.isEmpty(email.text.toString()) ||
                TextUtils.isEmpty(password.text.toString()) ||
                TextUtils.isEmpty(cpassword.text.toString()) ||
                TextUtils.isEmpty(question.text.toString()) ||
                TextUtils.isEmpty(answer.text.toString()))
            {
                Toast.makeText(this@Register, "Please fill all field!", Toast.LENGTH_SHORT).show()
            }
            else if (password.text.toString() != cpassword.text.toString()){
                Toast.makeText(this@Register, "Password doesn't match!", Toast.LENGTH_SHORT).show()
            }
            else{
                val emailReg = email.text.toString()
                val passwordReg = password.text.toString()
                val usernameReg = username.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailReg, passwordReg)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->
                            if(task.isSuccessful)
                            {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                val profileUpdates = userProfileChangeRequest {
                                    displayName = usernameReg
                                    photoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/journall-19c42.appspot.com/o/avatar%2Favatar-g035793718_640.png?alt=media&token=5ee8dca8-8b26-4daf-8d6d-2bcb1c6b0743")
                                }

                                firebaseUser!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User profile updated")
                                    }
                                }

                                Toast.makeText(this@Register, "Registration success.",
                                    Toast.LENGTH_SHORT).show()

                                FirebaseAuth.getInstance().signOut()

                                val intent = Intent(this@Register, Login::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
//                                intent.putExtra("email", emailReg)
//                                intent.putExtra("username", firebaseUser.displayName)
//                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@Register, "Registration failed.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
            }

        }
    }
//    private fun registerUser(String email, String password){
//
//    }
}