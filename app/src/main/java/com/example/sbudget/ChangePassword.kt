package com.example.sbudget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        changePassword()

    }

    private fun changePassword() {
        val newPasswordFrom : EditText = findViewById(R.id.editPasswordNew)
        val changePasswordBtn : Button = findViewById(R.id.changePassword)

        val user = FirebaseAuth.getInstance().currentUser

        changePasswordBtn.setOnClickListener {
            if(newPasswordFrom.text.toString().length < 6) {
                Toast.makeText(baseContext, "Min password length should be 6 characters!", Toast.LENGTH_SHORT).show()
            } else {
                user!!.updatePassword(newPasswordFrom.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Password updated.", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Something wrong. Try again", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}