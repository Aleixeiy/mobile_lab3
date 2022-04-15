package com.example.cat

interface SongRepository
{
    suspend fun insert(song: Song)

    suspend fun getAllSongs(): List<Song>
}