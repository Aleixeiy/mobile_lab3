package com.example.cat

class SongRepositoryImpl (private val songDAO: SongDAO): SongRepository
{
    override suspend fun insert(song: Song) {
        songDAO.insert(song)
    }

    override suspend fun getAllSongs(): List<Song> = songDAO.getAllSongs()
}