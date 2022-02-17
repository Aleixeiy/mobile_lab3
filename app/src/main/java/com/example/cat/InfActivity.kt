package com.example.cat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class InfActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inf)

        val b = findViewById<Button>(R.id.infButton)
        b.setOnClickListener( View.OnClickListener
        {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        })
    }
}