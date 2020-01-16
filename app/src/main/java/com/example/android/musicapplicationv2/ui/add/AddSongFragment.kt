package com.example.android.musicapplicationv2.ui.add

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.ui.playmusic.PlayMusicViewModel

class AddSongFragment : Fragment() {

    private lateinit var addSongViewModel: AddSongViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addSongViewModel =
            ViewModelProviders.of(this).get(AddSongViewModel::class.java)
        val root = inflater.inflate(R.layout.add_song_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_add_song)
        addSongViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}
