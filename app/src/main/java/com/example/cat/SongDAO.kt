package com.example.cat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDAO {
    @Insert
    suspend fun insert(song: Song)

    @Query("SELECT * FROM ${Song.TABLE_NAME}")
    suspend fun getAllSongs(): List<Song>
}