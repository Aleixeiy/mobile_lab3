package com.example.cat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class HistActivity : AppCompatActivity() {

    private var pref: SharedPreferences? = null
    private var name: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hist)

        name = intent.getStringExtra("name").toString()
        pref = getSharedPreferences(name, Context.MODE_PRIVATE)

        var keys = mutableListOf<String>()
        pref?.all?.keys?.forEach { it ->
            if ((it.length == 10) && (it[2] == '.') && (it[5] == '.'))
                keys.add(it)
        }
        keys = sort(keys)
        val linevalues = ArrayList<Entry>()
        if (keys.size > 0) {
            val startDate = LocalDate.parse(keys[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            keys.forEach { it ->
                val date = LocalDate.parse(it, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                val num = Period.between(startDate, date).days + 1
                linevalues.add(Entry(num.toFloat(), pref?.getInt(it, 0)!!.toFloat() + 1))
            }
        }

        val linedataset = LineDataSet(linevalues, "First")
        linedataset.setDrawValues(false)

        linedataset.setDrawFilled(true)
        linedataset.color = Color.GREEN

        //We connect our data to the UI Screen
        val data = LineData(linedataset)
        var chr_hist = findViewById<LineChart>(R.id.chr_hist)
        setStyle(chr_hist)
        chr_hist.data = data

    }

    fun setStyle(lineChart: LineChart)
    {
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart.axisRight.isEnabled = false

        lineChart.legend.isEnabled = false

        lineChart.description.isEnabled = false

        lineChart.animateX(1000, Easing.EaseInSine)

        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

        val yAxis: YAxis = lineChart.axisLeft
        yAxis.granularity = 1f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 5f

        lineChart.setBackgroundColor(resources.getColor(R.color.white))
        lineChart.animateXY(2000, 2000, Easing.EaseInCubic)

    }

    fun onMainClick(view: View)
    {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    fun sort (keys : MutableList<String>): MutableList<String>
    {
        var i = keys.size - 2
        while (i >= 0)
        {
            for (j in 0..i)
            {
                var date1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.parse(keys[j], DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                var date2 = LocalDate.parse(keys[j + 1], DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                if (date1.isAfter(date2))
                {
                    var temp = keys[j]
                    keys[j] = keys[j + 1]
                    keys[j + 1] = temp
                }
            }
            i--
        }

        return keys
    }
}