package com.example.android.musicapplicationv2.ui.playmusic

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository
import kotlinx.coroutines.launch

class PlayMusicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository

    private lateinit var timer : CountDownTimer

    private var _timeLeftString = MutableLiveData<String>()
    var timeLeftString : MutableLiveData<String>
        get() = _timeLeftString
        set(value) {
            _timeLeftString = value
        }

    private var _titleAndArtist = MutableLiveData<String>()
    var titleAndArtist : MutableLiveData<String>
        get() = _titleAndArtist
        set(value) {
            _titleAndArtist = value
        }

    private var _timeLeft = MutableLiveData<Long>()
    var timeLeft : MutableLiveData<Long>
        get() = _timeLeft
        set(value) {
            _timeLeft = value
        }

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
            } else {
                stopPlaying() //if already playing and this is the last song
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
        if(currentlyPlaying.value!!) {
            continuePlaying()
        } else
            stopPlaying()
    }

    fun updateTitleAndArtistString() {
        titleAndArtist.value = currentSong.value?.song_title + " - " + currentSong.value?.artist
    }

    fun updateTimeLeftString() {
        timeLeftString.value = DateUtils.formatElapsedTime(timeLeft.value!!)
    }

    fun updateSongFromNavigation(id: Long) {
        viewModelScope.launch {
            if(id != (-1).toLong()) {
                currentSong.value = repository.get(id)
                startPlaying()
            }
        }
    }

    private fun continuePlaying() {
        currentlyPlaying.value = true
        continueTimer()
    }

    private fun startPlaying() {
        currentlyPlaying.value = true
        startTimer()
    }

    private fun stopPlaying() {
        currentlyPlaying.value = false
        timer.cancel()
    }

    private fun startTimer() {
        createTimer(currentSong.value?.duration?.toLong())
        timer.start()
    }

    private fun continueTimer() {
        createTimer(timeLeft.value)
        timer.start()
    }

    fun updateTimeLeft() {//song change
        if(currentlyPlaying.value!!) {
            timer.cancel() //cancel previous timer
            startTimer() //start new one
        }
        this.timeLeft.value = currentSong.value?.duration?.toLong() //to display time left before clicking on play
    }

    private fun createTimer(time: Long?) {
        var millis = time?.times(1000)
        timer = object : CountDownTimer(millis ?:0L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft.value = millisUntilFinished / 1000L
            }
            override fun onFinish() {
                onNextClicked()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}