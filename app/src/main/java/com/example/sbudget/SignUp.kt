package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUp : Button = findViewById(R.id.btnSignUp)
        val fName : TextView = findViewById(R.id.editTxtFname)
        val lName : TextView = findViewById(R.id.editTxtLname)
        val uName : TextView = findViewById(R.id.editTxtUname)
        val password1 : TextView = findViewById(R.id.editTextPassword)
        val password2 : TextView = findViewById(R.id.editTextPassword2)
        val phoneNumber : TextView = findViewById(R.id.editTxtPhone)
        val eAddress : TextView = findViewById(R.id.editTxtAddress)


        btnSignUp.setOnClickListener {
            val firstName = fName.text.toString().trim()
            val lastName = lName.text.toString().trim()
            val userName = uName.text.toString().trim()
            val password = password1.text.toString().trim()
            val passwordR = password2.text.toString().trim()
            val phone = phoneNumber.text.toString().trim()
            val email = eAddress.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty() || passwordR.isEmpty()
                || phone.isEmpty() || email.isEmpty()
            ) {
                Toast.makeText(this, "Field must be fill!", Toast.LENGTH_SHORT).show()
            }else if(password != passwordR) {
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
            }else{
                val passHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
                Log.d("register", "$passHash")

                val data = UserData(firstName, lastName, userName, passHash, phone, email, "NotSub")
                FirebaseDatabase.getInstance().getReference("Users/$userName/").setValue(data)
                    .addOnSuccessListener {
                        Toast.makeText(baseContext, "Account created!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, IncomeAndExpense::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(baseContext, "Something wrong, please try again later or contact with us", Toast.LENGTH_SHORT).show()
                    }
            }
        }


        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
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