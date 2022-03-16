package com.example.chatreborn

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpReborn : AppCompatActivity() {
    lateinit var txt_email_sign_up: EditText
    lateinit var txt_password_sing_up: EditText
    lateinit var btn_sign_up: Button
    lateinit var txt_have_acc: TextView
    lateinit var auth: FirebaseAuth
    lateinit var txt_name: EditText
    lateinit var DbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_reborn)
        supportActionBar?.hide()                                                                    // hide action bar from the activity

        txt_email_sign_up = findViewById(R.id.txt_email_sign_up)
        txt_password_sing_up = findViewById(R.id.txt_password_sign_up)
        txt_have_acc = findViewById(R.id.txt_already_acc)
        btn_sign_up = findViewById(R.id.btn_sign_up)
        auth = FirebaseAuth.getInstance()
        txt_name = findViewById(R.id.txt_name_sign_up)



        btn_sign_up.setOnClickListener {
            Toast.makeText(this, "Creating account", Toast.LENGTH_SHORT).show()
            val name = txt_name.text.toString()
            val email = txt_email_sign_up.text.toString()
            val password = txt_password_sing_up.text.toString()
            if(name == "" || email == "" || password =="" ) Toast.makeText(this,
                "Please enter all the details" , Toast.LENGTH_LONG).show()
            else signUp(name, email, password)
        }
        txt_have_acc.setOnClickListener {
            val intent = Intent(this, LoginReborn::class.java)
            startActivity(intent)
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Sing Up Successful",
                        Toast.LENGTH_SHORT).show()
                    addUserToDatabase(name , email, auth.currentUser?.uid!!)
                    val intent = Intent(this, LoginReborn::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(this, task.exception.toString(),Toast.LENGTH_LONG).show()
                }
            }
    }
    fun addUserToDatabase(name : String , email : String , uid : String){
        DbRef = FirebaseDatabase.getInstance().reference
        DbRef.child("user").child(uid).setValue(user(name, email, uid))
    }
}
