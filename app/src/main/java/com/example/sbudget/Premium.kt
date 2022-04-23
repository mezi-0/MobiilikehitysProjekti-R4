package com.example.sbudget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.sbudget.databinding.ActivityPremiumBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

class Premium : AppCompatActivity() {

    lateinit var binding: ActivityPremiumBinding
    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)

        // Adding .xml fragments to tabLayout
        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MyBudget(), "My Budget")
        //adapter.addFragment(Memberships(), "My Memberships")
        pager.adapter = adapter
        tab.setupWithViewPager(pager)

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
    }
}