package com.example.android.musicapplicationv2.ui.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.data.Song
import com.example.android.musicapplicationv2.databinding.ListMusicFragmentBinding
import com.example.android.musicapplicationv2.ui.MyListAdapter

class ListMusicFragment : Fragment() {

    private var titleartist: ArrayList<String> = ArrayList()
    private var otherinfo: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ListMusicFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.list_music_fragment, container, false
        )

        var firstList = arrayOfNulls<String>(titleartist.size)
        titleartist.toArray(firstList)
        var secondList = arrayOfNulls<String>(otherinfo.size)
        otherinfo.toArray(secondList)

        val listV = binding.root.findViewById(R.id.song_list_view) as ListView
        listV.adapter = MyListAdapter(activity as Activity, firstList, secondList)
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
                 formatAndAddSong(song)
             }
        } catch (e: java.lang.reflect.InvocationTargetException){} //if there are no arguments
    }

    private fun formatAndAddSong(song: Song) {
        titleartist.add(song.song_title + " - " + song.artist)
        otherinfo.add(song.album + ": " + song.year + "  (" + formatDuration(song.duration) + ")")
    }

    private fun formatDuration(sec: Int) : String {
        var min = sec / 60
        var sec = sec - min * 60
        return "$min:$sec"
    }
}
