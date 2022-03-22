package com.example.mobiilikehitys_login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val actionBar = supportActionBar

        actionBar!!.title = "Sign up"

        actionBar.setDisplayHomeAsUpEnabled(true)


    }
}