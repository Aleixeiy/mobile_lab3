package com.example.cat

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer

class LoadActivity : AppCompatActivity() {
    var name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        name = intent.getStringExtra("name").toString()
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.putExtra("name", name)


        val image = findViewById<ImageView>(R.id.loadCat);
        image.setBackgroundResource(R.drawable.load_animation);
        val ani = image.background as AnimationDrawable
        val progress = findViewById<ProgressBar>(R.id.progress)
        ani.start()
        val timer = object : CountDownTimer(3000, 29) {
            override fun onTick(millisUntilFinished: Long) {
                progress.progress += 1
            }

            override fun onFinish() {
                ani.stop()
                startActivity(mainIntent)
            }
        }
        timer.start();
    }

    fun getLastResult(): Int
    {
        var pref = getSharedPreferences(name, Context.MODE_PRIVATE)
        val date = SimpleDateFormat("dd.MM.yyyy").format(Date()).toString()
        var count = pref?.getInt(date, 0)!!
        return count
    }
}