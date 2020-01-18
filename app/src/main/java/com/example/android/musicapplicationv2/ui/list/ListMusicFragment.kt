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
import androidx.navigation.fragment.navArgs
import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.databinding.ListMusicFragmentBinding
import com.example.android.musicapplicationv2.ui.MyListAdapter
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
        var x = listV.adapter
        listV.setOnItemClickListener { parent, view, position, id ->
            val popupMenu: PopupMenu = PopupMenu(activity!!.applicationContext, view)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_play ->
                        //Song = repository.data[position])
                        //Toast.makeText(this@MainActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        doSomething()

                    R.id.action_delete ->
                        //Toast.makeText(this@MainActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        viewModel.deleteSong((listV.adapter as MyListAdapter).getSongs()[position])
                    R.id.action_modify ->
                        //Toast.makeText(this@MainActivity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        doSomething()
                }
                true
            })
            popupMenu.show()
        }
        return binding.root
    }

    private fun doSomething() {

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
