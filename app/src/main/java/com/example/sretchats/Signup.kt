package com.example.sretchats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {

    private lateinit var edt_uname: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var signup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        edt_uname = findViewById(R.id.edt_uname)
        edtEmail = findViewById(R.id.edt_email)
        edtpassword = findViewById(R.id.edt_password)
        signup = findViewById(R.id.signup)


        signup.setOnClickListener {
            val name = edt_uname.text.toString()
            val email = edtEmail.text.toString()
            val password = edtpassword.text.toString()


            signUp(name,email,password);
        }
    }
    private fun signUp(name: String, email: String, password: String){
        //creating user

        //val final_name = name.lowercase()
        //val finalUname = final_name.replace("\\s".toRegex(), "")
        //val uniqueid = uniqueid.lowercase()
        //val finalUname = Regex("@sret.edu.in").toString()
        //val sret = "@sret.edu.in"
        //val final_name =email.contains(sret)
        val str2 = "@sret.edu.in"

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(email.contains(str2)){
                if (task.isSuccessful) {
                    //code for jumping to home


                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@Signup, MainActivity::class.java)
                    finish()
                    startActivity(intent)}
                else {
                    Toast.makeText(this@Signup, "Some error occured,Please Try again", Toast.LENGTH_SHORT).show()

                }




                } else{
                    Toast.makeText(this@Signup, "Sign up with SRET email id", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name: String, email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }


}