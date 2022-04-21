package com.example.sbudget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.sbudget.data.IaE
import com.example.sbudget.data.IaE_DAO
import com.example.sbudget.data.IaE_DATABASE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class IncomeAndExpense_AddNew : AppCompatActivity() {

    // Spinner variable
    lateinit var spinnerType : Spinner
    lateinit var db : IaE_DATABASE
    lateinit var iaeDao : IaE_DAO
    lateinit var user : FirebaseUser
    lateinit var userId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_and_expense_add_new)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Spinner implementation

        spinnerType = findViewById(R.id.type_selector)
        var spinnerCategory : Spinner = findViewById(R.id.category_selector)
        var spinnerTypeResult : String = ""
        var spinnerTypeResultCategory : String = ""
        var title: EditText = findViewById(R.id.title)
        var cost: EditText = findViewById(R.id.cost)
        var createBtn: Button = findViewById(R.id.addNew)


        // Initializing database
        db = Room.databaseBuilder(
            applicationContext,
            IaE_DATABASE::class.java, "IaE_DB"
        ).allowMainThreadQueries().build()

        // Initializing DAO
        iaeDao = db.iae_dao()

        val types = arrayOf("Income","Expense")
        val categories = arrayOf("Food & Dining", "Medical", "Housing", "Entertainment", "Another")
        val arrayAdapterType = ArrayAdapter(this, R.layout.spinner_list, types)
        val arrayAdapterCategory = ArrayAdapter(this, R.layout.spinner_list, categories)


        arrayAdapterType.setDropDownViewResource(R.layout.spinner_list)
        arrayAdapterCategory.setDropDownViewResource(R.layout.spinner_list)
        spinnerType.adapter = arrayAdapterType
        spinnerCategory.adapter = arrayAdapterCategory

        spinnerType.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerTypeResult = types[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        spinnerCategory.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerTypeResultCategory = categories[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinnerTypeResultCategory = "No category"
            }
        }

            createBtn.setOnClickListener() {

                if (title.text.isEmpty() || cost.text.isEmpty()) {
                    Toast.makeText(baseContext, "Field must be fill", Toast.LENGTH_SHORT).show()
                }
                else {
                    user = FirebaseAuth.getInstance().currentUser!!
                    userId = user.uid
                    if (userId != null) {
                        if (spinnerTypeResult == "Expense" && spinnerTypeResult != "Income") {
                            var costE: Double = cost.text.toString().toDouble()
                            costE *= -1
                            iaeDao.addIaE(
                                IaE(
                                    0,
                                    userId,
                                    spinnerTypeResultCategory,
                                    title.text.toString(),
                                    costE.toString(),
                                    spinnerTypeResult
                                )
                            )
                        } else if (spinnerTypeResult == "Income" && spinnerTypeResult != "Expense") {
                            val costI: Double = cost.text.toString().toDouble()
                            iaeDao.addIaE(
                                IaE(
                                    0,
                                    userId,
                                    "+",
                                    title.text.toString(),
                                    costI.toString(),
                                    spinnerTypeResult
                                )
                            )
                        }

                        val intent = Intent(this, IncomeAndExpense::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            baseContext,
                            "Something wrong, please try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, IncomeAndExpense::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}