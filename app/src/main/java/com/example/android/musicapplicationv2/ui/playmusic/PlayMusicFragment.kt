package com.example.android.musicapplicationv2.ui.playmusic

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.example.android.musicapplicationv2.R
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
            playMusicViewModel.continuePlayingIfAlready()
            playMusicViewModel.updateSongDurationString()
        })
        playMusicViewModel.seekbarProgressValue.observe(this, Observer {
            playMusicViewModel.updateTimeString()
        })
        playMusicViewModel.currentlyPlaying.observe(this, Observer {
            updatePlayStopIcon()
        })
        playMusicViewModel.updateSongFromNavigation(getIdArg())
        updateTimeLeftFromSeekbar()


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
        val playStopImgView = binding.root.findViewById<ImageView>(R.id.play_stop)
        if(playMusicViewModel.currentlyPlaying.value!!)
            playStopImgView.setImageResource(R.drawable.stop)
        else
            playStopImgView.setImageResource(R.drawable.play)
    }

    private fun updateTimeLeftFromSeekbar() {
        val seekbar = binding.root.findViewById<SeekBar>(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                playMusicViewModel.stopTimer()
            }

            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playMusicViewModel.setSeekbarProgressValue(seekBar?.progress!!)
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                playMusicViewModel.updateTimeString()
            }
        })
    }
}
