package com.example.android.musicapplicationv2.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.databinding.DataBindingUtil

import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.databinding.AddSongFragmentBinding

class AddSongFragment : Fragment() {

    private lateinit var addSongViewModel: AddSongViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddSongFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_song_fragment, container, false
        )

        binding.addSongButton.setOnClickListener {
            findNavController().navigate(AddSongFragmentDirections.actionNavigationAddToNavigationList())
        }
        return binding.root
    }
}
