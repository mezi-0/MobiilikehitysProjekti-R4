package com.example.sbudget


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.sbudget.data.IaE_DAO
import com.example.sbudget.data.IaE_DATABASE
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MyBudget : Fragment() {

    lateinit var piechart: PieChart
    lateinit var db: IaE_DATABASE
    lateinit var iaeDao: IaE_DAO
    lateinit var pieDataSet: PieDataSet
    lateinit var pieData: PieData
    var user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    var userId: String = user.uid

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.activity_my_budget, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        piechart = view.findViewById(R.id.myBudget_piechart)

        // Initializing SQLite database
        db = Room.databaseBuilder(
            requireContext(),
            IaE_DATABASE::class.java, "IaE_DB"
        ).allowMainThreadQueries().build()

        // Initializing DAO
        iaeDao = db.iae_dao()
        setupPieChartData()
        loadPieChartData()
    }

    // First setup PieChart, then loading it to page
    fun setupPieChartData() {
        piechart.isDrawHoleEnabled = true
        piechart.setUsePercentValues(true)
        piechart.setEntryLabelTextSize(13f)
        piechart.setEntryLabelColor(Color.BLACK)
        piechart.centerText = "Expense by Categories"
        piechart.setCenterTextSize(26f)
        piechart.description.isEnabled = false
    }


    fun loadPieChartData() {

        // Adding few variables for adding data from SQLite to Array
        var amount: String = ""
        var category: String = ""
        val delim = ":"

        // Get lasts ten data from SQLite and adding it to 2 Strings.
        // Then using delimiter to create Arrays
        // If 'cost' < 0 - Adds it to variable, if 'cost' => 0 - skip current data and go to next check
        iaeDao.readTenLasts(userId).forEach() {
            if (it.cost.toFloat() < 0) {
                amount += "${it.cost}:"
                category += "${it.category}:"
            }
        }

        val amountArray = amount.split(delim).toTypedArray()
        val categoryArray = category.split(delim).toTypedArray()
        val chartList: ArrayList<PieEntry> = ArrayList()


        // Creating variables for costs
        var foodAndDining: Float = 0f
        var medical: Float = 0f
        var housing: Float = 0f
        var entertainment: Float = 0f
        var another: Float = 0f
        var withOutCategory: Float = 0f

        // Checking IaE for category and amount. Adds all same categories to one cost
        if(amountArray[0].isEmpty()) {
        } else {
        for (i in 0 until amountArray.size - 1) {
            when {
                categoryArray[i] == "Food & Dining" -> {
                    foodAndDining += amountArray[i].toFloat()
                }
                categoryArray[i] == "Medical" -> {
                    medical += amountArray[i].toFloat()
                }
                categoryArray[i] == "Housing" -> {
                    housing += amountArray[i].toFloat()
                }
                categoryArray[i] == "Entertainment" -> {
                    entertainment += amountArray[i].toFloat()
                }
                categoryArray[i] == "Another" -> {
                    another += amountArray[i].toFloat()
                }
                else -> {
                    withOutCategory += amountArray[i].toFloat()
                }
            }
        }

        // If cost variable (sum of all costs that's have same category) < 0 - Adds to PieChart that category and cost
        // And converting cost to positive number, so PieChart will not get negative values
        if (foodAndDining < 0) {
            foodAndDining *= (-1)
            chartList.add(PieEntry(foodAndDining, "Food & Dining"))
        }
        if (medical < 0) {
            medical *= (-1)
            chartList.add(PieEntry(medical, "Medical"))
        }
        if (housing < 0) {
            housing *= (-1)
            chartList.add(PieEntry(housing, "Housing"))
        }
        if (entertainment < 0) {
            entertainment *= (-1)
            chartList.add(PieEntry(entertainment, "Entertainment"))
        }
        if (another < 0) {
            another *= (-1)
            chartList.add(PieEntry(another, "Another"))
        }
        if (withOutCategory < 0) {
            withOutCategory *= (-1)
            chartList.add(PieEntry(withOutCategory, "Without category"))
        }

        // Sets multiply color to PieChart from ColorTemplate (MPAndroidChart)
        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        /* BarChart settings list */
        pieDataSet = PieDataSet(chartList, "Expense Category")
        pieDataSet.colors = colors
        pieData = PieData(pieDataSet)

        pieData.setDrawValues(true)
        pieData.setValueFormatter(PercentFormatter(piechart))
        pieData.setValueTextSize(18f)

        pieData.setValueTextColor(Color.BLACK)
        piechart.data = pieData
        piechart.invalidate()
    }
    }

}