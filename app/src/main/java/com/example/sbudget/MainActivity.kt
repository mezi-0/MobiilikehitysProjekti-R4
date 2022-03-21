package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manageSubscriptionBtn =  findViewById<Button>(R.id.manageSubscriptionBtn)
        manageSubscriptionBtn.setOnClickListener {
            val intent = Intent(this, ManageSubscription::class.java)
            startActivity(intent)
        }
    }
}