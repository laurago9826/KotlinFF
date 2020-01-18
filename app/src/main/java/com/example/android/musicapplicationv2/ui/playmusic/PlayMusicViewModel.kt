package com.example.android.musicapplicationv2.ui.playmusic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository
import com.example.android.musicapplicationv2.ui.formatDuration
import kotlinx.coroutines.launch

class PlayMusicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository

    private var _currentSong = MutableLiveData<Song?>()
    var currentSong : MutableLiveData<Song?>
        get() = _currentSong
        set(value) {
            _currentSong = value
        }

    private var _titleAndArtist = MutableLiveData<String>()
    var titleAndArtist : MutableLiveData<String>
        get() = _titleAndArtist
        set(value) {
            _titleAndArtist = value
        }

    private var _duration = MutableLiveData<String>()
    var duration : MutableLiveData<String>
        get() = _duration
        set(value) {
            _duration = value
        }


    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository = SongRepository(dataSource)
        viewModelScope.launch {
            currentSong.value = repository.next(-1) //get first item
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            var fromDb = repository.next(currentSong.value?.id ?: -1)
            if(fromDb != null) {
                currentSong.value = fromDb
            }
        }
    }

    fun onPreviousClicked() {
        viewModelScope.launch {
            var fromDb = repository.previous(currentSong.value?.id ?: -1)
            if(fromDb != null) {
                currentSong.value = fromDb
            }
        }
    }

    fun updateTitleAndArtistString() {
        titleAndArtist.value = currentSong.value?.song_title + " - " + currentSong.value?.artist
    }

    fun updateDurationString() {
        duration.value = formatDuration(currentSong.value?.duration!!)
    }
}