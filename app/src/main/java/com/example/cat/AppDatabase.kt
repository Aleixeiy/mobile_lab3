package com.example.cat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database
(
    entities = [Song::class], version = 1
)

abstract class AppDatabase: RoomDatabase()
{
    abstract fun songDao(): SongDAO

    companion object
    {
        fun buildDatabase(context: Context, dbName: String): AppDatabase
        {
            return Room.databaseBuilder(context,AppDatabase::class.java, dbName).build()
        }
    }
}