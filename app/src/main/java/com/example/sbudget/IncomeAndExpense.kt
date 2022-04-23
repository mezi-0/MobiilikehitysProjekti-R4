package com.example.sbudget

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.sbudget.data.IaE
import com.example.sbudget.data.IaE_DAO
import com.example.sbudget.data.IaE_DATABASE
import com.example.sbudget.databinding.ActivityIncomeAndExpenseBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class IncomeAndExpense : AppCompatActivity() {

    lateinit var binding: ActivityIncomeAndExpenseBinding
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData
    lateinit var db: IaE_DATABASE
    lateinit var iaeDao : IaE_DAO

    // Get userId from firebase. For each user different data from SQLite by userId
    var user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    var userId: String = user.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomeAndExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing database
        db = Room.databaseBuilder(
            applicationContext,
            IaE_DATABASE::class.java, "IaE_DB"
        ).allowMainThreadQueries().build()

        // Initializing DAO
        iaeDao = db.iae_dao()

        // Bottom navigation menu
        binding.bNav.selectedItemId = R.id.ic_graph
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_person -> {
                    val intent = Intent(this, MyProfile::class.java)
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

        // Button which open new activity for adding new IaE (IncomeAndExpense)
        val addNewIaEBtn =  findViewById<Button>(R.id.addTulotMenotBtn)
        addNewIaEBtn.setOnClickListener {
            val intent = Intent(this, IncomeAndExpense_AddNew::class.java)
            startActivity(intent)
            finish()
        }

        // .xml TextViews initialization
        val text0: TextView = findViewById(R.id.tulotMenotText1)
        val text1: TextView = findViewById(R.id.tulotMenotText2)
        val text2: TextView = findViewById(R.id.tulotMenotText3)
        val text3: TextView = findViewById(R.id.tulotMenotText4)
        val text4: TextView = findViewById(R.id.tulotMenotText5)
        val text5: TextView = findViewById(R.id.tulotMenotText6)

        // Creating Array for all TextView elements for easier using when combines data from SQLite
        val textArray = arrayListOf<TextView>(
            text0,
            text1,
            text2,
            text3,
            text4,
            text5
        )

        // Get lasts six data from SQLite and adding it to one String. Then using delimiter to create Array
        var str: String = ""
        val delim = ":"
        iaeDao.readSixLasts(userId).forEach() {
            str += "${it.title}, " + "${it.cost} €, " + "${it.category} :"
        }
        val strArray = str.split(delim).toTypedArray()

        // If Array is empty - get FATAL error and app close, but with this if() statement app stays opened without error
        // Add elements in TextView Array, if data from SQLite not empty
        if(strArray[0].isEmpty()) {
            // Nothing to do
        } else {
            for (i in 0 until strArray.size - 1) {
                textArray[i].text = strArray[i]
            }
            chartCreating()
        }
    }

    // Creating BarChart, which use data from SQLite
    private fun chartCreating() {

        // Initializing BarChart widget
        val barChart = findViewById<BarChart>(R.id.barChart)
        var amount: String = ""
        val delim = ":"

        // Get lasts ten data from SQLite and adding it to one String. Then using delimiter to create Array
        iaeDao.readTenLasts(userId).forEach() {
            amount += "${it.cost}:"
        }

        var amountArray = amount.split(delim).toTypedArray()
        val barList: ArrayList<BarEntry> = ArrayList()

        for (i in 0 until amountArray.size-1) {
            barList.add(BarEntry(i.toFloat(), amountArray[i].toFloat()))
        }



        /* BarChart settings list */

        // BarChart initializing and title for Chart
        barDataSet = BarDataSet(barList, "IncomeAndExpense Diagram")
        barData = BarData(barDataSet)

        // BarChart colors. Can be multiply colors
        barDataSet.setColors(Color.parseColor("#304567"))

        barChart.data = barData
        barChart.invalidate()
        barChart.setBackgroundColor(Color.rgb(255,255,255))

        // Disable background grid-lines and legend
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.xAxis.setDrawGridLines(false)
        barChart.legend.isEnabled=false

        // Disable description at bottom left
        barChart.description.isEnabled = false

        // BarChart text color and size
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

    }
}

