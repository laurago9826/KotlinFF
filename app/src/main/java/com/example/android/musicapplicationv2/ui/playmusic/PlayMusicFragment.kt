package com.example.android.musicapplicationv2.ui.playmusic

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import com.example.android.musicapplicationv2.R

class PlayMusicFragment : Fragment() {

    private lateinit var playMusicViewModel: PlayMusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playMusicViewModel =
            ViewModelProviders.of(this).get(PlayMusicViewModel::class.java)
        val root = inflater.inflate(R.layout.play_music_fragment, container, false)

        val textView: TextView = root.findViewById(R.id.text_play_music)
        playMusicViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}
