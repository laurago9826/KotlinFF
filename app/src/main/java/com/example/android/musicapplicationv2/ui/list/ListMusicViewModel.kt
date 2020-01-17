package com.example.android.musicapplicationv2.ui.list

import android.app.Application
import android.text.Spanned
import androidx.lifecycle.*
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository

class ListMusicViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository

    //val songStrings : LiveData<Spanned>

    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository= SongRepository(dataSource)
        val songs=repository.songs
        /*List<String> songString =
        songString = Transformations.map(songs) { items ->
            formatNights(items, application.resources)
        }*/
    }

    private fun parseSongsToStrings() : List<String>{
        val songs = repository.songs.value
        var songsString = ArrayList<String>()
        songs?.forEach{
            songsString.add(it.song_title+" - "+it.artist+"\n"+it.album + ": "+it.year+"\t("+it.duration+")")
        }
        return songsString
    }
}