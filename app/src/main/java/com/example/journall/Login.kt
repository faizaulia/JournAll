package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val pendaftaran = findViewById<TextView>(R.id.tv_sign)
        val forgot = findViewById<TextView>(R.id.tv_forget)
        val email = findViewById<EditText>(R.id.edEmail)
        val password = findViewById<EditText>(R.id.ed_password)
        val loginBtn = findViewById<Button>(R.id.btn_login)

        pendaftaran.setOnClickListener{
            intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }
        forgot.setOnClickListener {
            intent = Intent(this@Login, forgotPassword::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            if (TextUtils.isEmpty(email.text.toString()) ||
                TextUtils.isEmpty(password.text.toString()))
            {
                Toast.makeText(this@Login, "Please fill all field!", Toast.LENGTH_SHORT).show()
            } else {
                val emailLogin: String = email.text.toString()
                val passwordLogin: String = password.text.toString()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailLogin, passwordLogin)
                    .addOnCompleteListener { task->
                        if (task.isSuccessful) {
                            Toast.makeText(this@Login, "Login success.", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@Login, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("email", emailLogin)
                            intent.putExtra("username", FirebaseAuth.getInstance().currentUser!!.displayName)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Login, "Login failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }
