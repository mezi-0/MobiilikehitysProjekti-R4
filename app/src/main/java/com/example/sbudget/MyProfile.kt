package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)



        val navigationMenu : BottomNavigationView = findViewById(R.id.bottom_navigation)
        when (navigationMenu.id) {
            R.id.ic_graph -> startActivity(Intent(this, IncomeAndExpense::class.java))
            R.id.ic_person -> startActivity(Intent(this, MyProfile::class.java))
        }

        val manageSubscriptionBtn =  findViewById<Button>(R.id.manageSubscriptionBtn)
        manageSubscriptionBtn.setOnClickListener {
            val intent = Intent(this, ManageSubscription::class.java)
            startActivity(intent)
        }

        val changePasswordBtn = findViewById<Button>(R.id.changePasswordBtn)
        changePasswordBtn.setOnClickListener {
            val intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id == R.id.ic_graph) {

            val intent = Intent (this, IncomeAndExpense::class.java)
            finish()
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}