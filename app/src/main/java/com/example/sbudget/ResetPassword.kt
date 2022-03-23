package com.example.sbudget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val actionBar = supportActionBar

        actionBar!!.title = "Reset Password"

        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}