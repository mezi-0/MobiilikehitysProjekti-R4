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


class IncomeAndExpense : AppCompatActivity() {

    lateinit var binding: ActivityIncomeAndExpenseBinding
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData
    lateinit var db: IaE_DATABASE
    lateinit var iaeDao : IaE_DAO


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


        binding.bNav.selectedItemId = R.id.ic_graph
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_person -> {
                    val intent = Intent(this, MyProfile::class.java)
                    finish()
                    startActivity(intent)
                }
            }
            true
        }

        val addNewIaEBtn =  findViewById<Button>(R.id.addTulotMenotBtn)
        addNewIaEBtn.setOnClickListener {
            val intent = Intent(this, IncomeAndExpense_AddNew::class.java)
            startActivity(intent)
        }



        val text0: TextView = findViewById(R.id.tulotMenotText1)
        val text1: TextView = findViewById(R.id.tulotMenotText2)
        val text2: TextView = findViewById(R.id.tulotMenotText3)
        val text3: TextView = findViewById(R.id.tulotMenotText4)
        val text4: TextView = findViewById(R.id.tulotMenotText5)
        val text5: TextView = findViewById(R.id.tulotMenotText6)

        val textArray = arrayListOf<TextView>(
            text0,
            text1,
            text2,
            text3,
            text4,
            text5
        )
        Log.d("kekis", textArray[0].text.toString())
        var str: String = ""
        val delim = ":"

        iaeDao.readSixLasts().forEach() {
            str += "${it.type}, " + "${it.title}, " + "${it.cost} €:"
        }

        val strArray = str.split(delim).toTypedArray()

        Log.d("kekis", strArray[0])
        if(strArray[0].isEmpty()) {

        } else {
            for (i in 0 until strArray.size - 1) {
                textArray[i].text = strArray[i]
            }

            chartCreating()

        }
    }

    private fun chartCreating() {

        val barChart = findViewById<BarChart>(R.id.barChart)
        var amount: String = ""
        val delim = ":"

        Log.d("kekis", "Test")
        iaeDao.readSixLasts().forEach() {
            amount += "${it.cost}:"
        }

        var amountArray = amount.split(delim).toTypedArray()
        val barList: ArrayList<BarEntry> = ArrayList()

        for (i in 0 until amountArray.size-1) {
            val barEntry = BarEntry(i.toFloat(), amountArray[i].toFloat())

            barList.add(barEntry)
        }


        // List settings
        barDataSet = BarDataSet(barList, "IncomeAndExpense Diagram")


        barData = BarData(barDataSet)
        //barDataSet.setColors(Color.parseColor("#304567"))

        barDataSet.setColors(Color.parseColor("#304567"))
        barChart.data = barData

        barChart.invalidate()
        barChart.setBackgroundColor(Color.rgb(255,255,255))

        // Disable background grid lines
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.xAxis.setDrawGridLines(false)
        barChart.legend.isEnabled=false

        barChart.description.isEnabled = false

        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

    }


    // Toolbar
    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id == R.id.ic_person) {

            val intent = Intent (this, MyProfile::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
*/
}

