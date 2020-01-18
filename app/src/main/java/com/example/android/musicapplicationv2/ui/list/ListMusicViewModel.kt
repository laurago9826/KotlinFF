package com.example.android.musicapplicationv2.ui.list

import android.app.Application
import androidx.lifecycle.*
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository
import kotlinx.coroutines.launch

class ListMusicViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository
    //val songsString = LiveData<List<String>>()
    val songs : LiveData<List<Song>>

    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository =SongRepository(dataSource)
        songs = repository.songs

    }

     fun addSong(song: Song) {
         viewModelScope.launch {
             repository.insert(song)
         }
    }

    fun getTitleAndArtistArray() : LiveData<Array<String>> {
        val songs : LiveData<List<Song>> = repository.songs
        return Transformations.map(songs) {
            getTitleAndArtistArrayHelper(it)
        }
    }

    private fun getTitleAndArtistArrayHelper(songss: List<Song>) : Array<String> {
        var str = ArrayList<String>()
        for(s in songss) {
            str.add(s.song_title + " - " + s.artist)
        }
        return str.toTypedArray()
    }


    fun getOtherInfoArray() : LiveData<Array<String>> {
        val songs : LiveData<List<Song>> = repository.songs
        return Transformations.map(songs) { items ->
            getOtherInfoArrayHelper(items)
        }
    }

    private fun getOtherInfoArrayHelper(songss: List<Song>) : Array<String> {
        var str = ArrayList<String>()
        for(s in songss) {
            str.add(s.album + ": " + s.year + "  (" + formatDuration(s.duration) + ")")
        }
        return str.toTypedArray()
    }

    private fun formatDuration(sec: Int) : String {
        var min = sec / 60
        var sec = sec - min * 60
        return "$min:$sec"
    }
}