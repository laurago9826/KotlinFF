package com.example.android.musicapplicationv2.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class ListMusicViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is list music Fragment"
    }
    val text: LiveData<String> = _text
}