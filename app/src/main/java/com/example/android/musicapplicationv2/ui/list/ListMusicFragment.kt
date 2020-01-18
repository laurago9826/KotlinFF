package com.example.android.musicapplicationv2.ui.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.databinding.AddSongFragmentBinding
import com.example.android.musicapplicationv2.databinding.ListMusicFragmentBinding
import com.example.android.musicapplicationv2.ui.MyListAdapter
import com.example.android.musicapplicationv2.ui.add.AddSongFragmentDirections
import java.text.FieldPosition

class ListMusicFragment : Fragment() {

    lateinit var viewModel: ListMusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ListMusicViewModel::class.java)
        val binding: ListMusicFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.list_music_fragment, container, false
        )

        val listV = binding.root.findViewById(R.id.song_list_view) as ListView
        viewModel.songs.observe(this, Observer { songs ->
            listV.adapter = MyListAdapter(activity as Activity, songs)
        })

        listV.setOnItemClickListener { _, view, position, _ ->
            val popupMenu = PopupMenu(activity!!.applicationContext, view)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                var song = (listV.adapter as MyListAdapter).getSongs()[position]
                when(item.itemId) {
                    R.id.action_play -> navigateToPlay(song.id)
                    R.id.action_delete -> viewModel.deleteSong(song)
                    R.id.action_modify -> navigateToModify(song.id)
                }
                true
            })
            popupMenu.show()
        }
        return binding.root
    }

    private fun navigateToPlay(id: Long) {
        val action =
            ListMusicFragmentDirections.actionNavigationListToNavigationPlayMusic(id)
        findNavController().navigate(action)
    }

    private fun navigateToModify(id: Long) {
        val action =
            ListMusicFragmentDirections.actionNavigationListToNavigationAdd(id)
        findNavController().navigate(action)
    }
}
