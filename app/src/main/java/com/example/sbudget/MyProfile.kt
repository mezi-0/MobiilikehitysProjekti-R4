package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sbudget.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MyProfile : AppCompatActivity() {


    lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        

        binding.bNav.selectedItemId = R.id.ic_person
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_graph -> {
                    val intent = Intent(this, IncomeAndExpense::class.java)
                    finish()
                    startActivity(intent)
                }
                R.id.ic_money -> {
                    val intent = Intent(this, Premium::class.java)
                    finish()
                    startActivity(intent)
                }
            }
            true
        }


        val manageSubscriptionBtn = findViewById<Button>(R.id.manageSubscriptionBtn)
        manageSubscriptionBtn.setOnClickListener {
            val intent = Intent(this, ManageSubscription::class.java)
            startActivity(intent)
        }

        val changePasswordBtn = findViewById<Button>(R.id.changePasswordBtn)
        changePasswordBtn.setOnClickListener {
            val intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }

        val user : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val reference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val userID : String = user.uid

        val usernameText = findViewById<TextView>(R.id.usernameTextDB)
        val emailText = findViewById<TextView>(R.id.emailTextDB)
        val levelText = findViewById<TextView>(R.id.subscriptionTextDB)

        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Something wrong happened!", Toast.LENGTH_SHORT).show()
            }


            override fun onDataChange(snapshot: DataSnapshot) {
                var userName = snapshot.child("userName").getValue(String::class.java).toString()
                var email = snapshot.child("email").getValue(String::class.java).toString()
                var level = snapshot.child("level").getValue(String::class.java).toString()

                if(snapshot!!.exists()) {

                    usernameText.setOnClickListener {
                        Toast.makeText(baseContext, userName, Toast.LENGTH_SHORT).show()
                    }
                    emailText.setOnClickListener {
                        Toast.makeText(baseContext, email, Toast.LENGTH_SHORT).show()
                    }

                    if(userName.length > 15) {
                        usernameText.setText(userName.substring(0, 15) + "...")
                    } else if (userName.length <= 15) {
                        usernameText.setText(userName)
                    }
                    if(email.length > 15) {
                        emailText.setText(email.substring(0, 15) + "...")
                    } else if(email.length <= 15) {
                        emailText.setText(email)
                    }
                        levelText.setText(level)


                }

            }
        })

    }
}