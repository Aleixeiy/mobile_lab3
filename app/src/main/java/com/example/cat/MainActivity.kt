package com.example.cat
//https://ohmanda.com/api/horoscope/leo/
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {



    private var pref: SharedPreferences? = null
    private var prefCurrentUser: SharedPreferences? = null
    private var name: String = ""

    private var skb_mood: SeekBar? = null
    private var txt_mood: TextView? = null
    private var txt_title: TextView? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var prg_song: ProgressBar? = null
    private var btn_play: ImageButton? = null
    private var lnr_songs: LinearLayout? = null
    private var scr_songs: ScrollView? = null

    private  var songs: List<Song>? = null
    private var curr_song: Song? = null

    @SuppressLint("ClickableViewAccessibility", "SoonBlockedPrivateApi", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        scr_songs = findViewById(R.id.scr_songs)
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
        txt_title = findViewById(R.id.txt_title)
        val editor = pref?.edit()
        editor?.putString("lastWorkout", Date().time.toString())
        editor?.apply()

        var sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sListener = object : SensorEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSensorChanged(sEvent: SensorEvent?) {

                val value = sEvent?.values
                var x0 = pref?.getFloat("x", 0f)
                var y0 = pref?.getFloat("y", 0f)
                var z0 = pref?.getFloat("z", 0f)
                var x = value?.get(0)!! * 1.0f
                var y = value?.get(1)!! * 1.0f
                var z = value?.get(2)!! * 1.0F
                val editor = pref?.edit()
                editor?.remove("x")
                editor?.putFloat("x", x)
                editor?.apply()
                editor?.remove("y")
                editor?.putFloat("y", y)
                editor?.apply()
                editor?.remove("x")
                editor?.putFloat("z", z)
                editor?.apply()

                var lastWorkout = pref?.getString("lastWorkout", "")
                val lastDate = lastWorkout?.toLong()
                if ((Date().time - lastDate!! > 10000) &&
                        (((abs(x0!! - x) > 2)) || (abs(y0!! - y) > 2) || (abs(z0!! - z) > 2)))
                {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Разминка")
                        .setMessage("Мы заботимя о вашем здоровье, поэтому не забудьте сделать разминку для глаз :)")
                        .setPositiveButton(
                            "понятно",
                            DialogInterface.OnClickListener { dialog, id -> // Закрываем окно
                                dialog.cancel()
                            })
                    builder.create().show()

                    editor?.putString("lastWorkout", Date().time.toString())
                    editor?.apply()
                }

            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }

        }
        sManager.registerListener(sListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        txt_mood = findViewById(R.id.txt_mood)
        skb_mood = findViewById(R.id.skb_mood)

        GlobalScope.launch {
            var song1 = Song(R.raw.give_it_all, "give it all", "night", 0)
            var song2 = Song(R.raw.nevr_let_you_down, "never let you down", "night", 0)
            var song3 = Song(R.raw.one_love, "one love", "night", 0)
            var song4 = Song(R.raw.smokey, "smokey", "night", 0)
            var song5 = Song(R.raw.when_you_sheep, "when you sheep", "night", 0)

            var song6 = Song(R.raw.heaven, "heaven", "afternoon", 0)
            var song7 = Song(R.raw.keep_on_going, "keep on going", "afternoon", 0)
            var song8 = Song(R.raw.kruna, "kruna", "afternoon", 0)
            var song9 = Song(R.raw.nomiza, "nomiza", "afternoon", 0)
            var song10 = Song(R.raw.proud, "proud", "afternoon", 0)

            var song11 = Song(R.raw.arcade, "heaven", "afternoon", 1)
            var song12 = Song(R.raw.better_love, "keep on going", "afternoon", 1)
            var song13 = Song(R.raw.solo, "kruna", "afternoon", 1)
            var song14 = Song(R.raw.tamta, "nomiza", "afternoon", 1)
            var song15 = Song(R.raw.telemoveis, "proud", "afternoon", 1)

            var song16 = Song(R.raw.pali_sie, "pali sie", "afternoon", 2)
            var song17 = Song(R.raw.stay, "stay", "afternoon", 2)
            var song18 = Song(R.raw.truth, "truth", "afternoon", 2)
            var song19 = Song(R.raw.wake_up, "wake up", "afternoon", 2)
            var song20 = Song(R.raw.walking_out, "walking out", "afternoon", 2)

            var song21 = Song(R.raw.all_to_you, "all to you", "afternoon", 3)
            var song22 = Song(R.raw.chameleon, "chameleon", "afternoon", 3)
            var song23 = Song(R.raw.la_venda, "la venda", "afternoon", 3)
            var song24 = Song(R.raw.like_it, "like it", "afternoon", 3)
            var song25 = Song(R.raw.storm, "storm", "afternoon", 3)

            var song26 = Song(R.raw.friend, "friend", "afternoon", 4)
            var song27 = Song(R.raw.love_is_forever, "love is forever", "afternoon", 4)
            var song28 = Song(R.raw.say_na, "say na", "afternoon", 4)
            var song29 = Song(R.raw.she_got_me, "she got me", "afternoon", 4)
            var song30 = Song(R.raw.soldi, "soldi", "afternoon", 4)
            try {
                insertSong(song1)
                insertSong(song2)
                insertSong(song3)
                insertSong(song4)
                insertSong(song5)
                insertSong(song6)
                insertSong(song7)
                insertSong(song8)
                insertSong(song9)
                insertSong(song10)
                insertSong(song11)
                insertSong(song12)
                insertSong(song13)
                insertSong(song14)
                insertSong(song15)
                insertSong(song16)
                insertSong(song17)
                insertSong(song18)
                insertSong(song19)
                insertSong(song20)
                insertSong(song21)
                insertSong(song22)
                insertSong(song23)
                insertSong(song24)
                insertSong(song25)
                insertSong(song26)
                insertSong(song27)
                insertSong(song28)
                insertSong(song29)
                insertSong(song30)
            }
            catch (e: Exception)
            {

            }
            songs = getAllSongs()
            launch(Dispatchers.Main)
            {
                addRltLayouts(skb_mood!!.progress)
            }
            curr_song = songs?.first()
        }

        skb_mood?.progress = 0
        val mood = pref?.getInt(SimpleDateFormat("dd.MM.yyyy").format(Date()), 0)
        progressChanged(mood!!)
        addRltLayouts(mood)
        skb_mood?.progress = mood

        skb_mood?.setOnSeekBarChangeListener(object :

            SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean)
            {
                var progress = skb_mood?.progress
                progressChanged(progress!!)
                addRltLayouts(progress!!)
            }

            override fun onStartTrackingTouch(seek: SeekBar)
            {
            }

            override fun onStopTrackingTouch(seek: SeekBar)
            {
            }

        })



        btn_play = findViewById(R.id.btn_play)
        prg_song = findViewById(R.id.prg_song)
        prg_song!!.progress = 0
        val timer = object: CountDownTimer(1000000, 1000) {
            override fun onTick(millisUntilFinished: Long)
            {
                if (mMediaPlayer != null)
                {
                    prg_song!!.max = mMediaPlayer!!.duration
                    prg_song!!.progress = mMediaPlayer!!.currentPosition
                }
            }

            override fun onFinish()
            {

            }
        }
        timer.start()
    }

    fun progressChanged(progress: Int)
    {
        if (progress == 0)
        {
            txt_mood?.text = "плохое"
            txt_mood?.setTextColor(Color.rgb(255, 0, 0))
        } else
            if (progress == 1)
            {
                txt_mood?.text = "не очень"
                txt_mood?.setTextColor(Color.rgb(255, 140, 0))
            } else
                if (progress == 2)
                {
                    txt_mood?.text = "среднее"
                    txt_mood?.setTextColor(Color.rgb(255, 255, 0))
                } else
                    if (progress == 3)
                    {
                        txt_mood?.text = "хорошее"
                        txt_mood?.setTextColor(Color.rgb(160, 255, 0))
                    } else
                        if (progress == 4)
                        {
                            txt_mood?.text = "отличное"
                            txt_mood?.setTextColor(Color.rgb(0, 255, 0))
                        }

        val editor = pref?.edit()
        editor?.putInt(SimpleDateFormat("dd.MM.yyyy").format(Date()), progress!!)
        editor?.putInt("01.04.2022", 2)
        editor?.putInt("02.04.2022", 3)
        editor?.putInt("03.04.2022", 4)
        editor?.putInt("05.04.2022", 1)
        editor?.putInt("08.04.2022", 2)
        editor?.putInt("10.04.2022", 4)
        editor?.putInt("11.04.2022", 3)

        editor?.apply()
    }

    fun addRltLayouts(mood: Int)
    {
        lnr_songs = findViewById(R.id.lnr_songs)
        while (true)
        {
            try
            {
                lnr_songs!!.removeView(lnr_songs!!.children.first())
            }
            catch (e: Exception)
            {
                break
            }

        }
        if (songs != null)
        if (songs?.size!! > 0)
        {
        var j = 0
        val date = Date()
        val hours = date.hours
        var time = "night"
        if (hours >= 6) time = "morning"
        if (hours >= 12) time = "afternoon"
        if (hours >= 18) time = "evening"
        time = "afternoon"
        songs?.forEach {
            if ((it.mood == mood) && (it.time == time)) {
                val rlt = getRltLayout(it)
                j++
                if (j % 2 == 0)
                    rlt.setBackgroundColor(Color.argb(100, 70, 60, 60))
                lnr_songs!!.addView(rlt)
            }
        }
        }
    }


    fun getRltLayout(song: Song): RelativeLayout
    {
        var rlt = RelativeLayout(this)
        rlt.layoutParams =
            RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150)

        var txt = TextView(this)
        txt.setText(song.name)
        txt.setTextSize(20F)
        txt.width = 900
        txt.setTextColor(Color.WHITE)
        txt.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        txt.setPadding(0, 30, 0, 30)

        rlt.addView(txt)

        rlt.setOnClickListener {
            curr_song = song
            if (mMediaPlayer != null)
            mMediaPlayer!!.stop()
            mMediaPlayer = null
            playSound(findViewById(R.id.btn_play))
        }
        return rlt
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
                val infIntent = Intent(this, InfActivity::class.java)
                startActivity(infIntent)
                return true
            }
            R.id.action_help ->
            {
                val aboutIntent = Intent(this, HelpActivity::class.java)
                startActivity(aboutIntent)
                return true
            }
            R.id.action_hist ->
            {
                val histIntent = Intent(this, HistActivity::class.java)
                histIntent.putExtra("name", name)
                startActivity(histIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onProfileClick(view: View)
    {
        val profileIntent = Intent(this, ProfileActivity::class.java)
        profileIntent.putExtra("name", name)
        startActivity(profileIntent)
    }









    fun playSound(view: View) {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, curr_song!!.id)
            mMediaPlayer!!.isLooping = true
            btn_play!!.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            mMediaPlayer!!.start()
        } else
        {
            if (mMediaPlayer!!.isPlaying)
            {
                btn_play!!.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
                pauseSound()
            }
            else
            {
                btn_play!!.setBackgroundResource(R.drawable.ic_baseline_pause_24)
                mMediaPlayer!!.start()
            }
        }
    }

    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    // 3. Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        //if (mMediaPlayer != null) {
        //    mMediaPlayer!!.release()
        //    mMediaPlayer = null
        //}
    }





    suspend private fun insertSong(song: Song)
    {
        val request = GlobalScope.launch {
            try {
                (applicationContext as App).repository.insert(song = song)
            }
            catch (e: Exception)
            {

            }
        }
        request.join()
    }

    suspend private fun getAllSongs(): List<Song>
    {
        var songs: List<Song>? = null
        val request = GlobalScope.launch {
            songs = (applicationContext as App).repository.getAllSongs()
        }
        request.join()
        return songs!!
    }
}