package com.example.cat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*

class AchieveActivity : AppCompatActivity() {
    private var name: String = ""
    private var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achieve)
        name = intent.getStringExtra("name").toString()
        pref = getSharedPreferences(name, Context.MODE_PRIVATE)
        achieve10Times()
        achieveAllPages()
        achieve1000Times()
        archieveClient()
    }

    fun achieve10Times()
    {
        if (getAllSatiety() >= 10)
        {
            var imgAllPages = findViewById<ImageView>(R.id.imgAchieve10times)
            imgAllPages.setImageResource(R.drawable.achieve10)
        }
    }

    fun achieveAllPages()
    {
        if ((pref?.getBoolean("inf_was", false) == true) &&
            (pref?.getBoolean("hist_was", false) == true) &&
            (pref?.getBoolean("help_was", false) == true))
        {
            var imgAllPages = findViewById<ImageView>(R.id.imgAchieveAllPages)
            imgAllPages.setImageResource(R.drawable.archieveallpages)
        }
    }

    fun achieve1000Times()
    {
        if (getAllSatiety() >= 1000)
        {
            var imgAllPages = findViewById<ImageView>(R.id.imgAchieve1000times)
            imgAllPages.setImageResource(R.drawable.archieve1000)
        }
    }

    fun archieveClient()
    {
        if (getDaysCount() >= 7)
        {
            var imgAllPages = findViewById<ImageView>(R.id.imgAchieveClient)
            imgAllPages.setImageResource(R.drawable.archieveclient)
        }
    }

    fun getAllSatiety(): Int
    {
        var res = 0
        pref?.all?.keys?.forEach { it ->
            if ((it.length == 10) && (it[2] == '.') && (it[5] == '.'))
                res += pref?.getInt(it, 0)!!
        }

        return res
    }

    fun getDaysCount(): Int
    {
        var res = 0
        pref?.all?.keys?.forEach { it ->
            if ((it.length == 10) && (it[2] == '.') && (it[5] == '.'))
                res++
        }

        return res
    }

    fun onClick(view: View)
    {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
}