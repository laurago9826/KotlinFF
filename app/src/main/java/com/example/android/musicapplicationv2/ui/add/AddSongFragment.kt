package com.example.android.musicapplicationv2.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.databinding.AddSongFragmentBinding
import java.lang.reflect.InvocationTargetException

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
        this.addSongViewModel =
            ViewModelProviders.of(this).get(AddSongViewModel::class.java)
        binding.addSongViewModel = addSongViewModel
        binding.lifecycleOwner = this


        val idArgs = getIdArg()
        this.addSongViewModel.updateLiveDataOnCreation(idArgs)
        binding.addSongButton.setOnClickListener {
            addSongViewModel.addOrUpdateSong(idArgs)
            val action   = AddSongFragmentDirections.actionNavigationAddToNavigationList()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun getIdArg() : Long {
        return try {
            val args: AddSongFragmentArgs by navArgs()
            args.id //update
        } catch (e: InvocationTargetException) {
            -1 //insert
        }
    }
}