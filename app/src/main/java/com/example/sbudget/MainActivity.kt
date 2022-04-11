package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txtBtnReset : TextView = findViewById(R.id.button_reset)
        txtBtnReset.setOnClickListener{
            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
        }


        val btnSignUp : Button = findViewById(R.id.button_sign_up)
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }


        val btnLogin = findViewById<Button>(R.id.button_login)
        val username = findViewById<TextView>(R.id.text_username)
        val password = findViewById<TextView>(R.id.text_password)
        btnLogin.setOnClickListener {
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            FirebaseDatabase.getInstance().getReference("Users/$username/")
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()) {
                            val hash = snapshot.child("password").value.toString()
                            val result = BCrypt.verifyer().verify(password.toCharArray(), hash)
                            if(result.verified) {
                                startActivity(Intent(this@MainActivity, IncomeAndExpense::class.java))
                                finish()
                            }else {
                                Toast.makeText(baseContext, "Sorry, password is wrong!", Toast.LENGTH_SHORT).show()
                            }
                        }else {
                            Toast.makeText(baseContext, "Sorry, username not Found!", Toast.LENGTH_SHORT).show()
                        }
                    }
                })


        }
    }

}