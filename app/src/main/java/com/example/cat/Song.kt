package com.example.cat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cat.Song.Companion.TABLE_NAME

@Entity
(
    tableName = TABLE_NAME
)

data class Song (
    @PrimaryKey
    val id: Int,
    val name: String,
    val time: String,
    val mood: Int
) {

    companion object
    {
        const val TABLE_NAME = "Song"
        const val ID = "Id"
        const val NAME = "Name"
        const val TIME = "Time"
        const val MOOD = "Mood"
    }
}