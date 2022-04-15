package com.example.cat

import android.app.Application

class App: Application() {
    private lateinit var database: AppDatabase
    lateinit var repository: SongRepository

    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.buildDatabase(this, DATABASE_NAME)
        repository = SongRepositoryImpl(database.songDao())
    }

    companion object
    {
        val DATABASE_NAME = "RelaxApp"
    }

}