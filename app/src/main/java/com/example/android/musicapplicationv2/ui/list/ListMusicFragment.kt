package com.example.android.musicapplicationv2.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.ui.list.ListMusicViewModel

class ListMusicFragment : Fragment() {

    private lateinit var listMusicViewModel: ListMusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listMusicViewModel =
            ViewModelProviders.of(this).get(ListMusicViewModel::class.java)
        val root = inflater.inflate(R.layout.list_music_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_list_music)
        listMusicViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}
