package com.example.sbudget

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sbudget.databinding.ActivityIncomeAndExpenseBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class IncomeAndExpense : AppCompatActivity() {

    lateinit var binding: ActivityIncomeAndExpenseBinding
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomeAndExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chartCreating()

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
    }



    private fun chartCreating() {

        val barChart = findViewById<BarChart>(R.id.barChart)

        val barList: ArrayList<BarEntry> = ArrayList()
        val barList1: ArrayList<Double> = ArrayList()
        val barList2: ArrayList<Double> = ArrayList()

        for (i in 1..6) {
            barList1.add(i * 50.0)
        }
        for (i in 1..6) {
            barList2.add(i * -45.0)
        }
        for (i in 0 until barList1.size) {
            val barEntry = BarEntry(i.toFloat(), floatArrayOf(barList2.get(i).toFloat(), barList1.get(i).toFloat()))
            barList.add(barEntry)
        }

        // List settings
        barDataSet = BarDataSet(barList, "IncomeAndExpense Diagram")


        barData = BarData(barDataSet)
        //barDataSet.setColors(Color.parseColor("#304567"))

        barDataSet.setColors(Color.RED, Color.parseColor("#304567"))
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

