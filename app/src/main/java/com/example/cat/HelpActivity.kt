package com.example.cat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2

class HelpActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        var viewPager = findViewById<ViewPager2>(R.id.pager)
        var adapter = HelpAdapter(this)
        viewPager.adapter = adapter

        val b = findViewById<Button>(R.id.next)
        b.setOnClickListener(View.OnClickListener
        {


            var pager = findViewById<ViewPager2>(R.id.pager)
            if (pager.currentItem == 2)
            {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            } else
            {
                pager.currentItem++
            }


        })
    }
}