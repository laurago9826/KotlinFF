package com.example.android.musicapplicationv2.ui.playmusic

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        //tintOnTouch()


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

    private fun tintOnTouch() {
        val playStopImgView = binding.root.findViewById<ImageView>(R.id.play_stop)
        val prev = binding.root.findViewById<ImageView>(R.id.previous)
        val next = binding.root.findViewById<ImageView>(R.id.next)
        setOnTouchListenerforImg(playStopImgView)
        setOnTouchListenerforImg(prev)
        setOnTouchListenerforImg(next)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnTouchListenerforImg(imgView: ImageView) {
        imgView.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                imgView.setColorFilter(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
                return true
            }
        })
    }
}
