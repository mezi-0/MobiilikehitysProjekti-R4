package com.example.sbudget

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.sbudget.data.IaE_DAO
import com.example.sbudget.data.IaE_DATABASE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ManageSubscription : AppCompatActivity() {

    lateinit var db: IaE_DATABASE
    lateinit var iaeDao : IaE_DAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_subscription)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        statusGet()
    }

    private fun statusGet() {

        val user : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val reference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val userID : String = user.uid

        val status : TextView = findViewById(R.id.statusInfo)
        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var statusFrom = snapshot.child("level").getValue(String::class.java).toString()
                if(snapshot!!.exists()) {
                    status.text = statusFrom
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Something wrong happened!", Toast.LENGTH_SHORT).show()
            }
        })


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