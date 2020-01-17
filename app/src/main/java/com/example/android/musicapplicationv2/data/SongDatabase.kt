package com.example.android.musicapplicationv2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Song::class), version = 1,  exportSchema = false)
abstract class SongDatabase : RoomDatabase() {

    abstract val songDatabaseDao: SongDatabaseDao
    companion object {

        @Volatile
        private var INSTANCE: SongDatabase? = null
        fun getInstance(context: Context): SongDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "songs")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}