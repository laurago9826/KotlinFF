package com.example.android.musicapplicationv2.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddSongViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is add song Fragment"
    }
    val text: LiveData<String> = _text
}
