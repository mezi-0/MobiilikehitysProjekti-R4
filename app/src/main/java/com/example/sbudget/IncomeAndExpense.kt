package com.example.sbudget

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.graphics.Color

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class IncomeAndExpense : AppCompatActivity() {

    lateinit var barList: ArrayList<BarEntry>
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_and_expense)

        //!!! Charts !!!//


        val barChart = findViewById<BarChart>(R.id.barChart)
        barList=ArrayList()

        // 10 lines for barChart
        for (i in 0..10) {
            barList.add(BarEntry(i.toFloat(), (250..1250).random().toFloat()))
        }
        // List settings
        barDataSet = BarDataSet(barList, "Test")
        barData = BarData(barDataSet)
        barDataSet.setColors(Color.rgb(61,165,255))
        barChart.data = barData // Adding a list to barChart
        barDataSet.valueTextColor = Color.WHITE
        barDataSet.valueTextSize = 15f


        //!!! Charts !!!//

    }

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

}

