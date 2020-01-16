package com.example.android.musicapplicationv2.ui.playmusic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayMusicViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is play music Fragment"
    }
    val text: LiveData<String> = _text
}
