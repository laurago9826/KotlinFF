package com.example.android.musicapplicationv2.ui.add

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository
import kotlinx.coroutines.launch


class AddSongViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository

    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository = SongRepository(dataSource)
    }

    private var _title = MutableLiveData<String>()
    var title: MutableLiveData<String>
        get() = _title
        set(value) {
            _title = value
        }


    private var _artist = MutableLiveData<String>()
    var artist: MutableLiveData<String>
        get() = _artist
        set(value) {
            _artist = value
        }

    private var _album = MutableLiveData<String>()
    var album: MutableLiveData<String>
        get() = _album
        set(value) {
            _album = value
        }

    private var _year = MutableLiveData<Int>().apply { value = 0 }
    var year: MutableLiveData<Int>
        get() = _year
        set(value) {
            _year = value
        }

    private var _duration = MutableLiveData<String>().apply{value = "00:00"}
    var duration: MutableLiveData<String>
        get() = _duration
        set(value) {
            _duration = value
        }


    fun addOrUpdateSong(id: Long) {
        viewModelScope.launch {
            val songFromDb = repository.get(id)
            if(songFromDb == null)
                repository.insert(updateSongProperties(createEmptySong()))
            else
                repository.update(updateSongProperties(songFromDb))
        }
    }

    private fun createEmptySong() : Song {
        return Song(song_title = "", artist = "", album = "", year = 0, duration = 0)
    }

    private fun updateSongProperties(song: Song) : Song {
        song.song_title = _title.value ?: ""
        song.artist = _artist.value ?: ""
        song.album = _album.value ?: ""
        song.year = _year.value ?: 0
        song.duration = parseStringDuration(_duration.value) ?: 0
        return song
    }

    fun updateLiveDataOnCreation(id: Long) {
        var song : Song
        if(id != (-1).toLong()) {
            viewModelScope.launch {
                song = repository.get(id)!!
                _title.value = song.song_title
                _artist.value = song.artist
                _album.value = song.album
                _year.value = song.year
                _duration.value = DateUtils.formatElapsedTime(song.duration.toLong())
            }
        }
    }

    private fun parseStringDuration(duration: String?) : Int? {
        val minAndSec = duration?.split(':')
        var number = true
        if(null != minAndSec && minAndSec.size == 2) {
            var num1 = 0
            var num2 = 0
            try {
                num1 = Integer.parseInt(minAndSec[0])
                num2 = Integer.parseInt(minAndSec[1])
            } catch (e: NumberFormatException) {
                number = false
            }
            if (number && num1 < 100 && num2 < 60) {
                return num1*60 + num2
            }
        }
        return null //incorrect format or ranges
    }
}