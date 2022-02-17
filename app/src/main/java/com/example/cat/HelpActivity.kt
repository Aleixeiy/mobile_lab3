package com.example.cat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class HelpActivity : AppCompatActivity() {
    var helpNumber: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val b = findViewById<Button>(R.id.next)
        b.setOnClickListener(View.OnClickListener
        {
            val helpText = findViewById<TextView>(R.id.helpText)
            helpNumber++
            if (helpNumber == 2)
            {
                helpText.text = "Чтобы посмотреть результаты предыдущих игр, нажмите на пункт меню 'История'"
                val helpPic = findViewById<ImageView>(R.id.helpPic)
                helpPic.setImageResource(R.drawable.help2)
            }
            if (helpNumber == 3)
            {
                helpText.text = "Поделитесь результатом в социально сети вконтакте, нажав на  'Поделиться вк'"
                val helpPic = findViewById<ImageView>(R.id.helpPic)
                helpPic.setImageResource(R.drawable.help3)
            }
            if (helpNumber == 4)
            {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                val next = findViewById<Button>(R.id.next)
                next.text = "Продолжить"
            }
        })
    }
}