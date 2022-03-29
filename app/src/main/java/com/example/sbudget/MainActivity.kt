package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView


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
        btnLogin.setOnClickListener {
            val intent = Intent(this, IncomeAndExpense::class.java)
            finish()
            startActivity(intent)
        }
    }

}