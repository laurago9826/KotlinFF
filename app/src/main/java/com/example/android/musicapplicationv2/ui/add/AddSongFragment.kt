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


        var idArgs = getIdArg()
        addSongViewModel.updateLiveDataOnCreation(idArgs)
        binding.addSongButton.setOnClickListener {
            addSongViewModel.addOrUpdateSong(idArgs)
            val action   = AddSongFragmentDirections.actionNavigationAddToNavigationList()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun getIdArg() : Long {
        var id : Long
        try{
            val args: AddSongFragmentArgs by navArgs()
            id = args.id //update
        } catch (e: java.lang.reflect.InvocationTargetException) {
            id = -1 //insert
        }
        return id
    }
}
