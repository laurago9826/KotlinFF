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

    @Query("select * from songs where id > :key order by id asc limit 1")
    fun next(key: Long) : Song?

    @Query("select * from songs where id < :key order by id desc limit 1")
    fun previous(key: Long) : Song?
}