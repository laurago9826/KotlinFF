package com.example.android.musicapplicationv2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song (

    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,

    @ColumnInfo(name = "song_title")
    var song_title: String,

    @ColumnInfo(name = "artist")
    var artist: String,

    @ColumnInfo(name = "album")
    var album: String,

    @ColumnInfo(name = "year")
    var year: Int,

    @ColumnInfo(name = "duration")
    var duration: Int
    )
