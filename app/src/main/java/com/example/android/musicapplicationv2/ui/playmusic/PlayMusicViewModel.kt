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

    private var _currentlyPlaying = MutableLiveData<Boolean>().apply { value = false }
    var currentlyPlaying : MutableLiveData<Boolean>
        get() = _currentlyPlaying
        set(value) {
            _currentlyPlaying = value
        }

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

    private var _leftDuration = MutableLiveData<String>()
    var leftDuration : MutableLiveData<String>
        get() = _leftDuration
        set(value) {
            _leftDuration = value
        }


    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository = SongRepository(dataSource)
        viewModelScope.launch {
            currentSong.value = repository.next(-1) //get first item
        }
        currentlyPlaying.value = false
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

    fun onPlayStopClicked() {
        currentlyPlaying.value = !currentlyPlaying.value!!
    }

    fun updateTitleAndArtistString() {
        titleAndArtist.value = currentSong.value?.song_title + " - " + currentSong.value?.artist
    }

    fun updateDurationString() {
        leftDuration.value = formatDuration(currentSong.value?.duration!!)
    }

    fun updateSongFromNavigation(id: Long) {
        viewModelScope.launch {
            if(id != (-1).toLong()) {
                currentSong.value = repository.get(id)
                currentlyPlaying.value = true
            }
        }
    }
}