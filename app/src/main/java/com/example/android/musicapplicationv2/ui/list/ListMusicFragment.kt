package com.example.android.musicapplicationv2.ui.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.databinding.ListMusicFragmentBinding
import com.example.android.musicapplicationv2.ui.MyListAdapter

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
        addArgsFromAdd()

/*        val linearLayoutManager = LinearLayoutManager(activity!!.applicationContext, RecyclerView.VERTICAL,false)
        val recyclerView = binding.root.findViewById(R.id.song_list_view) as RecyclerView
        recyclerView.layoutManager = linearLayoutManager

        viewModel.songs.observe(this, Observer { songs ->
            recyclerView.adapter = RecyclerViewAdapter(songs)
        })*/
        val listV = binding.root.findViewById(R.id.song_list_view) as ListView

        viewModel.songs.observe(this, Observer { songs ->
            listV.adapter = MyListAdapter(activity as Activity, songs)
        })


        return binding.root
    }

    private fun addArgsFromAdd() {
        try{
            val argsFromAdd: ListMusicFragmentArgs by navArgs()
            if(argsFromAdd.artist != "" && argsFromAdd.artist !="") {
                val song = Song(
                    song_title = argsFromAdd.songTitle,
                    artist = argsFromAdd.artist,
                    album = argsFromAdd.album,
                    year = argsFromAdd.year,
                    duration = argsFromAdd.duration
                )
                viewModel.addSong(song)

            }
        } catch (e: java.lang.reflect.InvocationTargetException){} //if there are no arguments
    }
}
