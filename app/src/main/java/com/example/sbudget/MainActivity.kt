package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.actionbar_color_gradient))
        setContentView(R.layout.activity_main)

        // TextView on Login page. Opens 'ResetPassword' activity
        val txtBtnReset : TextView = findViewById(R.id.button_reset)
        txtBtnReset.setOnClickListener{
            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
            finish()
        }

        // Button on Login page. Opens 'SignUp' activity
        val btnSignUp : Button = findViewById(R.id.button_sign_up)
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        // Widgets from Login page
        val btnLogin = findViewById<Button>(R.id.button_login)
        val email = findViewById<TextView>(R.id.text_email)
        val password = findViewById<TextView>(R.id.text_password)

        // When button clicked - checks credentials from Firebase authentication.
        // If credentials from Firebase is OK - logins in app
        btnLogin.setOnClickListener {
            val email: String = email.text.toString().trim()
            val password: String = password.text.toString().trim()
            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(baseContext, "Field must be fill", Toast.LENGTH_SHORT).show()
            } else {
                Firebase.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this@MainActivity, IncomeAndExpense::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Failed to login! Please check your credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }



        }
    }

}
