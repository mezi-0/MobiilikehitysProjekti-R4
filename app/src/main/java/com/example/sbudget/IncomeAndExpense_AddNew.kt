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
    lateinit var spinner : Spinner
    lateinit var db: IaE_DATABASE
    lateinit var iaeDao : IaE_DAO
    lateinit var spinnerTypeResult: String
    lateinit var textViewOut: TextView
    lateinit var user: FirebaseUser
    lateinit var userId: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_and_expense_add_new)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Spinner implementation
        spinner = findViewById(R.id.type_selector)
        var title: TextView = findViewById(R.id.title)
        var cost: TextView = findViewById(R.id.cost)
        var createBtn: Button = findViewById(R.id.addNew)
        textViewOut = findViewById(R.id.textOutput)


        // Initializing database
        db = Room.databaseBuilder(
            applicationContext,
            IaE_DATABASE::class.java, "IaE_DB"
        ).allowMainThreadQueries().build()

        // Initializing DAO
        iaeDao = db.iae_dao()

        val types = arrayOf("Income", "Expense")
        val arrayAdapter = ArrayAdapter(this, R.layout.spinner_list, types)



        arrayAdapter.setDropDownViewResource(R.layout.spinner_list)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerTypeResult = types[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        createBtn.setOnClickListener() {

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
                            title.text.toString(),
                            costE.toString(),
                            spinnerTypeResult
                        )
                    )
                } else if (spinnerTypeResult == "Income" && spinnerTypeResult != "Expense") {
                    var costI: Double = cost.text.toString().toDouble()
                    iaeDao.addIaE(
                        IaE(
                            0,
                            userId,
                            title.text.toString(),
                            costI.toString(),
                            spinnerTypeResult
                        )
                    )
                }
                val intent = Intent(this, IncomeAndExpense::class.java)
                finish()
                startActivity(intent)
            }

        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, IncomeAndExpense::class.java)
                finish()
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*
    private fun updateDBView() {
        var dbText: String = ""
        iaeDao.readSixLasts().forEach() {
            dbText += "${it.type}" + "${it.title}" + "${it.cost}"
        }
        textViewOut.text = dbText
    }

     */

}