package com.example.android.musicapplicationv2.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongRepository(private val database: SongDatabaseDao) {

    val songs = database.getAllSongs()

    suspend fun insert(song: Song){
        withContext(Dispatchers.IO) {
            database.insert(song)
        }
    }

    suspend fun delete(song: Song){
        withContext(Dispatchers.IO) {
            database.delete(song)
        }
    }

    suspend fun update(song: Song){
        withContext(Dispatchers.IO) {
            database.update(song)
        }
    }

    suspend fun get(id: Long) : Song? {
        var fromDb : Song? = null
        withContext(Dispatchers.IO) {
            fromDb = database.get(id)
        }
        return fromDb
    }

    suspend fun next(id: Long) : Song? {
        var fromDb : Song? = null
        withContext(Dispatchers.IO) {
            fromDb = database.next(id)
        }
        return fromDb
    }

    suspend fun previous(id: Long) : Song? {
        var fromDb : Song? = null
        withContext(Dispatchers.IO) {
            fromDb = database.previous(id)
        }
        return fromDb
    }
}