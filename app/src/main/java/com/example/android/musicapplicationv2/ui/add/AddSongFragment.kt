package com.example.android.musicapplicationv2.ui.add

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.databinding.AddSongFragmentBinding
import com.example.android.musicapplicationv2.ui.playmusic.PlayMusicViewModel
import java.lang.Double.parseDouble
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddSongFragment : Fragment() {

    private lateinit var addSongViewModel: AddSongViewModel

    private lateinit var binding: AddSongFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.add_song_fragment, container, false
        )
        addSongViewModel =
            ViewModelProviders.of(this).get(AddSongViewModel::class.java)
        binding.addSongViewModel = addSongViewModel
        binding.lifecycleOwner = this


        binding.addSongButton.setOnClickListener {
            val action   = AddSongFragmentDirections.actionNavigationAddToNavigationList(
            addSongViewModel.title.value ?:"",
                addSongViewModel.artist.value ?:"",
                addSongViewModel.album.value ?:"",
                addSongViewModel.year.value ?:0,
                parseStringDuration(addSongViewModel.duration.value) ?: 0)
            findNavController().navigate(action)
        }


        return binding.root
    }

    private fun parseStringDuration(duration: String?) : Int? {
        var min_and_sec = duration?.split(':')
        var number = true
        if(min_and_sec != null &&  min_and_sec!!.size == 2 ) {
            var num1 = 0
            var num2 = 0
            try {
                num1 = Integer.parseInt(min_and_sec[0])
                num2 = Integer.parseInt(min_and_sec[1])
            } catch (e: NumberFormatException) {
                number = false
            }
            if (number && num1 < 100 && num2 < 60) {
                return num1*60 + num2
            }
        }
        return null
    }
}
