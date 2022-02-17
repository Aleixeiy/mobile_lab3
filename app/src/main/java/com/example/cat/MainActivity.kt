package com.example.cat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private var count: Int = 0
    private var pref: SharedPreferences? = null
    private var prefCurrentUser: SharedPreferences? = null
    private var name: String = ""

    @SuppressLint("ClickableViewAccessibility", "SoonBlockedPrivateApi", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefCurrentUser = getSharedPreferences("currentUser", Context.MODE_PRIVATE)
        if (intent.hasExtra("name")) {
            name = intent.getStringExtra("name")!!
            val editor = prefCurrentUser?.edit()
            editor?.putString("currentUser", name)
            editor?.apply()
        }
        else
            name = prefCurrentUser?.getString("currentUser", "")!!

        pref = getSharedPreferences(name, Context.MODE_PRIVATE)

        val date = SimpleDateFormat("dd.MM.yyyy").format(Date()).toString()
        count = pref?.getInt(date, 0)!!
        val last = findViewById<TextView>(R.id.lastResult)
        last.text = getLastResult().toString()
        val textCount = findViewById<TextView>(R.id.count)
        textCount.text = count.toString()
    }

    fun onFeedClick(view: View)
    {
        count++
        val textCount = findViewById<TextView>(R.id.count)
        textCount.text = count.toString()

        if (count % 15 == 0) {
            val image = findViewById<ImageView>(R.id.cat_animation);
            image.setBackgroundResource(R.drawable.cat_animation);
            val ani = image.background as AnimationDrawable
            ani.start()
            val timer = object : CountDownTimer(880, 880) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    ani.stop()
                }
            }
            timer.start();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveData(res: Int)
    {
        val editor = pref?.edit()
        val date = SimpleDateFormat("dd.MM.yyyy").format(Date()).toString()
        editor?.putInt(date, res)
        editor?.apply()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        saveData(count)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_inf ->
            {
                val editor = pref?.edit()
                editor?.putBoolean("inf_was", true)
                editor?.apply()
                val infIntent = Intent(this, InfActivity::class.java)
                startActivity(infIntent)
                return true
            }
            R.id.action_help ->
            {
                val editor = pref?.edit()
                editor?.putBoolean("help_was", true)
                editor?.apply()
                val aboutIntent = Intent(this, HelpActivity::class.java)
                startActivity(aboutIntent)
                return true
            }
            R.id.action_history ->
            {
                val editor = pref?.edit()
                editor?.putBoolean("hist_was", true)
                editor?.apply()
                var historyText: String = ""
                var keys = mutableListOf<String>()
                pref?.all?.keys?.forEach { it ->
                    if ((it.length == 10) && (it[2] == '.') && (it[5] == '.'))
                    keys.add(it)
                }
                keys = sort(keys)
                keys.forEach { it ->
                    historyText += it + "   " + pref?.getInt(it, 0)!! + "\n"
                }

                val historyIntent = Intent(this, HistoryActivity::class.java)
                historyIntent.putExtra("historyText", historyText)
                startActivity(historyIntent)
                return true
            }
            R.id.action_share ->
            {
                GlobalScope.launch {
                    share()
                }
            }
            R.id.action_achieve ->
            {
                val achieveIntent = Intent(this, AchieveActivity::class.java)
                achieveIntent.putExtra("name", name)
                startActivity(achieveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
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

    fun getLastResult(): Int
    {
        var keys = mutableListOf<String>()
        pref?.all?.keys?.forEach { it ->
            if ((it.length == 10) && (it[2] == '.') && (it[5] == '.'))
            keys.add(it)
        }
        keys = sort(keys)
        var lastDate = ""
        if (keys.size >= 2)
            lastDate = keys[keys.size - 2]
        var res = pref?.getInt(lastDate, 0)
        return res!!
    }

    suspend fun share() {
        var c: Collection<VKScope> = emptySet()
        c.plus(VKScope.FRIENDS)
        c.plus(VKScope.ADS)
        c.plus(VKScope.AUDIO)
        c.plus(VKScope.DOCS)
        c.plus(VKScope.EMAIL)
        c.plus(VKScope.GROUPS)
        c.plus(VKScope.MARKET)
        c.plus(VKScope.MESSAGES)
        c.plus(VKScope.NOTES)
        c.plus(VKScope.NOTIFICATIONS)
        c.plus(VKScope.NOTIFY)
        c.plus(VKScope.OFFLINE)
        c.plus(VKScope.PAGES)
        c.plus(VKScope.PHONE)
        c.plus(VKScope.PHOTOS)
        c.plus(VKScope.STATS)
        c.plus(VKScope.STATUS)
        c.plus(VKScope.STORIES)
        c.plus(VKScope.VIDEO)
        c.plus(VKScope.WALL)
        VK.initialize(applicationContext)
        //VK.login(this, c)
        var message = "Я набрал " + (count / 15).toString() + " очков в игре FeedMeCat!"
        URL(("https://api.vk.com/method/wall.post?access_token=ebd361e969c42b0cb2606a22b1b359bad2fe2084dab36aa03e90512f0ad10718b8ee1be322c06d3363333&v=5.81&owner_id=512308950&friends_only=1&from_group=1&message=" + message)).readText()

    }
}