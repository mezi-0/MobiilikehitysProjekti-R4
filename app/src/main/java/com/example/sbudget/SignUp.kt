package com.example.sbudget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val actionBar = supportActionBar

        actionBar!!.title = "Sign up"

        actionBar.setDisplayHomeAsUpEnabled(true)

    }
}