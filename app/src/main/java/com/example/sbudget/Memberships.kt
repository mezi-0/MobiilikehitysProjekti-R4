package com.example.sbudget

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sbudget.databinding.ActivityMembershipsBinding
import com.example.sbudget.databinding.ActivityMyBudgetBinding

class Memberships : AppCompatActivity() {
    lateinit var binding: ActivityMembershipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bNav.selectedItemId = R.id.ic_money
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_person -> {
                    val intent = Intent(this, MyProfile::class.java)
                    finish()
                    startActivity(intent)
                }
                R.id.ic_graph -> {
                    val intent = Intent(this, IncomeAndExpense::class.java)
                    finish()
                    startActivity(intent)
                }
            }
            true
        }
        val historymapBtn = findViewById<Button>(R.id.historyMapBtn)
        historymapBtn.setOnClickListener {
            val intent = Intent(this, Premium::class.java)
            startActivity(intent)

        }

        val myBudgetBtn = findViewById<Button>(R.id.myBudgetBtn)
        myBudgetBtn.setOnClickListener {
            val intent = Intent(this, Memberships::class.java)
            startActivity(intent)
        }
    }
}
