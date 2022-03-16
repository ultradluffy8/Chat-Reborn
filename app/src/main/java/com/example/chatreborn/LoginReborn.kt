package com.example.chatreborn

import android.app.AlertDialog
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

class LoginReborn : AppCompatActivity() {
    lateinit var txt_email_log_in: EditText
    lateinit var txt_password_log_in: EditText
    lateinit var btn_log_in: Button
    lateinit var txt_forget_pswd: TextView
    lateinit var txt_register: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_reborn)
        supportActionBar?.hide()

        txt_email_log_in = findViewById(R.id.txt_email_log_in)
        txt_password_log_in = findViewById(R.id.txt_password_log_in)
        btn_log_in = findViewById(R.id.btn_log_in)
        txt_forget_pswd = findViewById(R.id.txt_forget_password)
        txt_register = findViewById(R.id.txt_register)
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_log_in.setOnClickListener {
            if (txt_email_log_in.text.isEmpty()) {
                Toast.makeText(this, "Please Enter the Email address",
                    Toast.LENGTH_SHORT).show()
            }
            if (txt_password_log_in.text.isEmpty()) {
                Toast.makeText(this, "Please Enter the Password",
                    Toast.LENGTH_SHORT).show()
            }
            if (!txt_password_log_in.text.isEmpty() and !txt_password_log_in.text.isEmpty()) {
                val email = txt_email_log_in.text.toString()
                val password = txt_password_log_in.text.toString()
                login(email, password)
            }

        }
        txt_register.setOnClickListener {
            val intent = Intent(this, SignUpReborn::class.java)
            finish()
            startActivity(intent)
        }

    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {

                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "User does not exist",
                        Toast.LENGTH_SHORT).show()
                    notRegisteredDialog(email)
                }
            }
    }

    private fun notRegisteredDialog(email_provided: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("CONFIRMATION")
        dialog.setMessage(
            "No account has been found with the provided email " +
                    email_provided +
                    ". Do you want to create the account"
        )
        dialog.setPositiveButton("Hell Yeah") { _, _ ->
            val intent = Intent(this, SignUpReborn::class.java)
            startActivity(intent)
        }
        dialog.setNegativeButton("NO THANKS") { _, _ ->
            Toast.makeText(this, "BAKAYARO", Toast.LENGTH_LONG).show()
        }
        dialog.show()
    }
}

