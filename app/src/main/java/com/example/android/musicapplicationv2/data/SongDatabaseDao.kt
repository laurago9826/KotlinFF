package com.example.android.musicapplicationv2.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SongDatabaseDao{

    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("select * from songs where id = :key")
    fun get(key: Long): Song?

    @Query("select * from songs")
    fun getAllSongs(): LiveData<List<Song>>
}