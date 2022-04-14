package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val btnSignUp : Button = findViewById(R.id.btnSignUp)
        val uName : TextView = findViewById(R.id.editTxtUname)
        val password1 : TextView = findViewById(R.id.editTextPassword)
        val password2 : TextView = findViewById(R.id.editTextPassword2)
        val eAddress : TextView = findViewById(R.id.editTxtAddress)
        val progressBar : ProgressBar = findViewById(R.id.progressBar)

        btnSignUp.setOnClickListener {

            progressBar.visibility = View.VISIBLE

            val userName = uName.text.toString().trim()
            val password = password1.text.toString().trim()
            val passwordR = password2.text.toString().trim()
            val email = eAddress.text.toString().trim()

            if (userName.isEmpty() || password.isEmpty() || passwordR.isEmpty() || email.isEmpty()
            ) {
                Toast.makeText(this, "Field must be fill!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }else if(password != passwordR) {
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }else{

                // val passHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
                val data = UserData(userName, email, "0")
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task: Task<AuthResult> ->
                        if(task.isSuccessful){
                            //Registration OK
                            // FirebaseDatabase.getInstance().getReference("Users/$userName/").setValue(data)
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().uid.toString()).setValue(data)
                                .addOnSuccessListener {
                                    Toast.makeText(baseContext, "Account created!", Toast.LENGTH_SHORT).show()
                                    progressBar.visibility = View.INVISIBLE
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener {
                                    progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(baseContext, "Something wrong with database, please try again later or contact with us (@someemail)", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            //Registration error
                            Toast.makeText(baseContext, "Something wrong, try again later or change email", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.INVISIBLE
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