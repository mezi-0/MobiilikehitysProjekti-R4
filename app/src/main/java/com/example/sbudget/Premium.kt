package com.example.sbudget
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sbudget.databinding.ActivityPremiumBinding

class Premium : AppCompatActivity() {

    lateinit var binding: ActivityPremiumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
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
        val manageSubscriptionBtn =  findViewById<Button>(R.id.myBudgetBtn)
        manageSubscriptionBtn.setOnClickListener {
            val intent = Intent(this, MyBudget::class.java)
            startActivity(intent)

        }

        val changePasswordBtn = findViewById<Button>(R.id.membershipsBtn)
        changePasswordBtn.setOnClickListener {
            val intent = Intent(this, Memberships::class.java)
            startActivity(intent)
        }
    }
}