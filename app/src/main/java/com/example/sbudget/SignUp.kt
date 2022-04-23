package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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

            if (userName.isEmpty() || password.isEmpty() || passwordR.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Field must be fill!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }
            // If password < 6 characters
            else if(password.length < 6){
                Toast.makeText(this, "Min password lenght should be 6 characters!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }
            // If repeated password wrong
            else if(password != passwordR) {
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }
            // If email-textView text does not match the correct model (Correct: example@example.com)
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }
            // If all fields is OK ->
            else{

                // Creating new user in Firebase authenticated users and Firebase database.
                val data = UserData(userName, email, "Not member")
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task: Task<AuthResult> ->
                        if(task.isSuccessful){
                            //Registration OK
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().uid.toString()).setValue(data)
                                .addOnSuccessListener {
                                    Toast.makeText(baseContext, "Account created!", Toast.LENGTH_SHORT).show()
                                    progressBar.visibility = View.INVISIBLE
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener {
                                    progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(baseContext, "Something wrong with database, please try again later", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            //Registration error, email already in Firebase
                            Toast.makeText(baseContext, "Email already exists", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.INVISIBLE
                        }
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