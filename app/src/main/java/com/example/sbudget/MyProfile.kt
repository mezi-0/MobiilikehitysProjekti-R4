package com.example.sbudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.sbudget.databinding.ActivityMyProfileBinding

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
    }
}


    /*
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

*/