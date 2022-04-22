package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetPassword : AppCompatActivity() {

    lateinit var emailEditText: EditText
    lateinit var sendEmailBtn: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        emailEditText = findViewById(R.id.text_email)
        sendEmailBtn = findViewById(R.id.button_reset)

        sendEmailBtn.setOnClickListener{
            sendEmail()
        }

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun sendEmail() {
        val email = emailEditText.text.toString().trim()
        if(email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please provide valid email", Toast.LENGTH_SHORT).show()
        } else {
            Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Check your email to reset your password", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}