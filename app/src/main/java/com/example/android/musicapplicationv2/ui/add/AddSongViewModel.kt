package com.example.android.musicapplicationv2.ui.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class AddSongViewModel : ViewModel() {

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
}