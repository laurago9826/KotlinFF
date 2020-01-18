package com.example.android.musicapplicationv2.ui.playmusic

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.databinding.AddSongFragmentBinding
import com.example.android.musicapplicationv2.databinding.PlayMusicFragmentBinding

class PlayMusicFragment : Fragment() {

    private lateinit var playMusicViewModel: PlayMusicViewModel

    private lateinit var binding: PlayMusicFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.play_music_fragment, container, false
        )
        playMusicViewModel =
            ViewModelProviders.of(this).get(PlayMusicViewModel::class.java)

        binding.playMusicViewModel = playMusicViewModel
        binding.lifecycleOwner = this

        playMusicViewModel.currentSong.observe(this, Observer {
            playMusicViewModel.updateTitleAndArtistString()
        })

        //binding.next.setOnClickListener{
        //    playMusicViewModel.onNextClicked()
        //}




        return binding.root
    }
}
