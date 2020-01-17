package com.example.android.musicapplicationv2.ui.list

import android.app.Application
import android.text.Spanned
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository

class ListMusicViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository
    //val songsString = LiveData<List<String>>()

    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository =SongRepository(dataSource)
        val songs = repository.songs
        //songsString = ""
    }








}