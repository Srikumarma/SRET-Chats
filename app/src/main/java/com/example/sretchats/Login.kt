package com.example.sretchats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var mAuth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtpassword = findViewById(R.id.edt_password)
        login = findViewById(R.id.login)
        signup = findViewById(R.id.signup)

        signup.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtpassword.text.toString()

            LogIn(email,password);
        }
    }

    private fun LogIn(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code ofr logging in user
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "Password or Email is incorrect", Toast.LENGTH_SHORT).show()
                }
            }

    }
}