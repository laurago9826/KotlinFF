package com.example.android.musicapplicationv2.ui.list

import android.app.Application
import androidx.lifecycle.*
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository
import kotlinx.coroutines.launch

class ListMusicViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository

    val songs : LiveData<List<Song>>

    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository = SongRepository(dataSource)
        songs = repository.songs
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            repository.delete(song)
        }
    }
}