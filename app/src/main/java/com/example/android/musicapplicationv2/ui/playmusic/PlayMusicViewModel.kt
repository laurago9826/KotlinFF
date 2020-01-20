package com.example.android.musicapplicationv2.ui.playmusic

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.data.SongDatabase
import com.example.android.musicapplicationv2.data.SongRepository
import kotlinx.coroutines.launch

class PlayMusicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : SongRepository

    private lateinit var timer : CountDownTimer

    private var _seekBarProgressValue  = MutableLiveData<Int>()
    var seekBarProgressValue : MutableLiveData<Int>
        get() = _seekBarProgressValue
    set(value) {
        _seekBarProgressValue = value
    }

    private var _timeString = MutableLiveData<String>()
    var timeString : LiveData<String> = _timeString

    private var _titleAndArtist = MutableLiveData<String>()
    var titleAndArtist : LiveData<String> = _titleAndArtist

    private var _songDuration = MutableLiveData<String>()
    var songDuration : LiveData<String> = _songDuration

    private var _currentlyPlaying = MutableLiveData<Boolean>().apply { value = false }
    var currentlyPlaying : MutableLiveData<Boolean> = _currentlyPlaying

    private var _currentSong = MutableLiveData<Song?>()
    var currentSong : MutableLiveData<Song?> = _currentSong



    init {
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao
        repository = SongRepository(dataSource)
        viewModelScope.launch {
            currentSong.value = repository.next(-1) //get first item
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val fromDb = repository.next(currentSong.value?.id ?: -1)
            if(fromDb != null) {
                currentSong.value = fromDb
            } else {
                if(currentlyPlaying.value!!) {
                    stopPlaying() //if already playing and this is the last song
                    seekBarProgressValue.value = 0

                }
            }
        }
    }

    fun onPreviousClicked() {
        val dur = currentSong.value?.duration
        if(dur ?: 0 > 3 && seekBarProgressValue.value!! > 3)
            resetProgress()
        else {
            viewModelScope.launch {
                val fromDb = repository.previous(currentSong.value?.id ?: -1)
                if (fromDb != null) {
                    currentSong.value = fromDb
                }
            }
        }
    }

    private fun resetProgress() {
        seekBarProgressValue.value = 0
        if(currentlyPlaying.value!!) {
            stopTimer()
            continueTimer()
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
        if(currentSong.value != null)
            _titleAndArtist.value = currentSong.value?.song_title ?: "" + " - " + currentSong.value?.artist ?: ""
        else
            _titleAndArtist.value = ""
    }

    fun updateSongDurationString() {
        _songDuration.value = DateUtils.formatElapsedTime(currentSong.value?.duration?.toLong() ?:0)
    }

    fun updateTimeString() {
        _timeString.value = DateUtils.formatElapsedTime(seekBarProgressValue.value?.toLong() ?: 0)
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

    fun stopTimer() {
        if(currentlyPlaying.value!!)
            timer.cancel()
    }

    private fun startTimer() {
        createTimer(currentSong.value?.duration?.toLong())
        timer.start()
    }

    private fun continueTimer() {
        createTimer(currentSong.value?.duration?.minus(seekBarProgressValue.value?.toLong() ?:0))
        timer.start()
    }

    fun setSeekBarProgressValue(dist: Int) {
        seekBarProgressValue.value = dist
        startAfterSeekBarSet()
    }

    private fun startAfterSeekBarSet(){
        if(currentlyPlaying.value!!)
            continueTimer()
    }

    fun continuePlayingIfAlready() {//song change
        seekBarProgressValue.value = 0
        if(currentlyPlaying.value!!) {
            timer.cancel() //cancel previous timer
            startTimer() //start new one
        }
    }

    private fun createTimer(time: Long?) {
        val millis = time?.times(1000)
        timer = object : CountDownTimer(millis ?:0L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                seekBarProgressValue.value = currentSong.value?.duration?.minus(millisUntilFinished / 1000L)?.toInt()
            }
            override fun onFinish() {
                onNextClicked()
            }
        }
    }
}