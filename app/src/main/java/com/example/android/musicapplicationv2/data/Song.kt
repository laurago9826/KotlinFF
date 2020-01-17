package com.example.android.musicapplicationv2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song (

    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,

    var song_title: String,

    var artist: String,

    var album: String,

    var year: Int,

    var duration: Long
    )