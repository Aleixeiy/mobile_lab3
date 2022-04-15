package com.example.cat

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.RemoteViews
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext
import kotlinx.android.synthetic.main.app_widget.*;
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.thread

/**
 * Implementation of App Widget functionality.
 */

class AppWidget : AppWidgetProvider() {
    companion object {
        private var mMediaPlayer: MediaPlayer? = null
        private var song = Song(R.raw.when_you_sheep, "bay", "afternoon", 4)
        private var AWI = 0
        private var AWM: AppWidgetManager? = null
        private var mood = 0
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    fun playSound(context: Context) {
        val views = RemoteViews(context.packageName, R.layout.app_widget)

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(context, song.id)
            mMediaPlayer!!.isLooping = true
            views.setImageViewResource(R.id.btn_wplay, R.drawable.ic_baseline_pause_24)
            mMediaPlayer!!.start()
        } else
        {
            if (mMediaPlayer!!.isPlaying)
            {
                views.setImageViewResource(R.id.btn_wplay, R.drawable.ic_baseline_play_arrow_24)
                pauseSound()
            }
            else
            {
                mMediaPlayer!!.start()
                views.setImageViewResource(R.id.btn_wplay, R.drawable.ic_baseline_pause_24)
            }
        }

        AWM?.updateAppWidget(AWI, views)
    }

    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)


        if (intent?.action == "play")
        {
            playSound(context!!)
        }
        if (intent?.action == "mood0")
        {
            mood = 0
            mMediaPlayer?.release()
            mMediaPlayer = null
            newSong(context!!)
            playSound(context)
        }
        if (intent?.action == "mood1")
        {
            mood = 1
            mMediaPlayer?.release()
            mMediaPlayer = null
            newSong(context!!)
            playSound(context)
        }
        if (intent?.action == "mood2")
        {
            mood = 2
            mMediaPlayer?.release()
            mMediaPlayer = null
            newSong(context!!)
            playSound(context)
        }
        if (intent?.action == "mood3")
        {
            mood = 3
            mMediaPlayer?.release()
            mMediaPlayer = null
            newSong(context!!)
            playSound(context)
        }
        if (intent?.action == "mood4")
        {
            mood = 4
            mMediaPlayer?.release()
            mMediaPlayer = null
            newSong(context!!)
            playSound(context)
        }


        val views = RemoteViews(context?.packageName, R.layout.app_widget)
        views.setTextViewText(R.id.txt_song, song.name)
        AWM?.updateAppWidget(AWI, views)
    }

    fun newSong(context: Context)
    {
        var songs: List<Song>? = null
        GlobalScope.launch {
            songs = getAllSongs(context!!)
        }
        while (songs == null)
            Thread.sleep(10)
        song = songs?.get(0)!!
    }

    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.app_widget)

        setListener(views, context, R.id.btn_mood0, "mood0")
        setListener(views, context, R.id.btn_mood1, "mood1")
        setListener(views, context, R.id.btn_mood2, "mood2")
        setListener(views, context, R.id.btn_mood3, "mood3")
        setListener(views, context, R.id.btn_mood4, "mood4")
        setListener(views, context, R.id.btn_wplay, "play")
        views.setImageViewResource(R.id.btn_wplay, R.drawable.ic_baseline_play_arrow_24)
        AWI = appWidgetId
        AWM = appWidgetManager
        appWidgetManager.updateAppWidget(appWidgetId, views)

    }

    suspend private fun getAllSongs(context: Context): List<Song>
    {
        var songs: List<Song> = emptyList<Song>()
        var song1 = Song(R.raw.heaven, "heaven", "afternoon", 0)
        var song2 = Song(R.raw.arcade, "arcade", "afternoon", 1)
        var song3 = Song(R.raw.pali_sie, "pali sie", "afternoon", 2)
        var song4 = Song(R.raw.all_to_you, "all to you", "afternoon", 3)
        var song5 = Song(R.raw.friend, "friend", "afternoon", 4)
        songs = songs.plus(song1)
        songs = songs.plus(song2)
        songs = songs.plus(song3)
        songs = songs.plus(song4)
        songs = songs.plus(song5)

        var songs_new: List<Song> = emptyList<Song>()

        val date = Date()
        val hours = date.hours
        var time = "night"
        if (hours >= 6) time = "morning"
        if (hours >= 12) time = "afternoon"
        if (hours >= 18) time = "evening"
        time = "afternoon"
        songs?.forEach {
            if ((it.mood == mood) && (it.time == time)) {
                songs_new = songs_new.plus(it)
            }
        }
        return songs_new
    }
}



fun setListener(views: RemoteViews, context: Context, id: Int, action: String)
{
    val intent = Intent(context, AppWidget::class.java)
    intent.action = action

    val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        0
    )

    views.setOnClickPendingIntent(id, pendingIntent)
}