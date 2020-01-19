package com.example.android.musicapplicationv2.ui.playmusic

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.databinding.AddSongFragmentBinding
import com.example.android.musicapplicationv2.databinding.PlayMusicFragmentBinding
import com.example.android.musicapplicationv2.ui.add.AddSongFragmentArgs

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
            playMusicViewModel.updateTimeLeft()
        })
        playMusicViewModel.timeLeft.observe(this, Observer {
            playMusicViewModel.updateTimeLeftString()
        })
        playMusicViewModel.currentlyPlaying.observe(this, Observer {
            updatePlayStopIcon()
        })
        playMusicViewModel.updateSongFromNavigation(getIdArg())



        return binding.root
    }

    private fun getIdArg() : Long {
        var id : Long
        try{
            val args: PlayMusicFragmentArgs by navArgs()
            id = args.id //update
        } catch (e: java.lang.reflect.InvocationTargetException) {
            id = -1 //insert
        }
        return id
    }

    private fun updatePlayStopIcon() {
        val playStopImgView = binding.root.findViewById(R.id.play_stop) as ImageView
        if(playMusicViewModel.currentlyPlaying.value!!)
            playStopImgView.setImageResource(R.drawable.stop)
        else
            playStopImgView.setImageResource(R.drawable.play)
    }
}
