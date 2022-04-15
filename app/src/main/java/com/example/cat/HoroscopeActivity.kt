package com.example.cat

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.net.URL

class HoroscopeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horoscope)
        var txt_horoscope = findViewById<TextView>(R.id.txt_horoscope)

        var response: String? = ""
        GlobalScope.launch {
            response = getForcasts()
            launch(Dispatchers.Main)
            {
                txt_horoscope.setText(response)
            }
        }

    }

    suspend fun getForcasts(): String?
    {
        var signs = setOf("aquarius", "pisces", "aries", "taurus", "gemini", "cancer", "leo", "virgo", "libra", "scorpio", "sagittarius", "capricorn")
        var text = ""
        signs.forEach {
            var result: String? = null
            var request = GlobalScope.launch {
                result = get("https://ohmanda.com/api/horoscope/".plus(it))
            }
            request.join()

            if (result == null) {
                Looper.prepare()
                val text = "Нет доступа в интернет"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this, text, duration)
                toast.show()
                Looper.loop()
                //return null
            }

            var gson = Gson()
            text = text.plus(it.toString().toUpperCase()).plus("\n").plus(gson.fromJson(result, Forecast::class.java).horoscope).plus("\n\n")
        }
        return text
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun get(url: String) : String?
    {
        //var response = pref?.getString("response", "")
        //if (response != null)
        //    return response!!

        var result: String = ""
        var url = URL(url)
        var request = GlobalScope.launch {
            try {
                result = url.readText()
            }
            catch(e: Exception)
            {
            }
        }
        request.join()
        if (result == "")
            return null

        //val editor = pref?.edit()
        //editor?.putString("response", result)
        //editor?.apply()

        return result
    }

    fun btnProfileClick(view: View)
    {
        val profileIntent = Intent(this, ProfileActivity::class.java)
        startActivity(profileIntent)
    }
}

