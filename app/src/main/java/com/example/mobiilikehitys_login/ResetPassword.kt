package com.example.mobiilikehitys_login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val actionBar = supportActionBar

        actionBar!!.title = "Reset Password"

        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}