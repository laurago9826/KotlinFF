package com.example.android.musicapplicationv2.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.R.layout
import com.example.android.musicapplicationv2.data.Song

class MyListAdapter(private val context: Activity, private val songs: List<Song>)
    : ArrayAdapter<String>(context, layout.song_templ, arrayOfNulls(songs.size)) {

    private val artistAndTitle = getTitleAndArtistArrayHelper(songs)
    private val otherInfo = getOtherInfoArrayHelper(songs)

    fun getSongs() : List<Song> {
        return songs
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(layout.song_templ, null, true)

        val titleArtistText = rowView.findViewById(R.id.title_and_artist) as TextView
        val otherInfoText = rowView.findViewById(R.id.other_info) as TextView

        titleArtistText.text = artistAndTitle[position]
        otherInfoText.text = otherInfo[position]

        return rowView
    }

    private fun getTitleAndArtistArrayHelper(songss: List<Song>) : Array<String> {
        var str = ArrayList<String>()
        for(s in songss) {
            str.add(s.song_title + " - " + s.artist)
        }
        return str.toTypedArray()
    }

    private fun getOtherInfoArrayHelper(songss: List<Song>) : Array<String> {
        var str = ArrayList<String>()
        for(s in songss) {
            str.add(s.album + ": " + s.year + "  (" + formatDuration(s.duration) + ")")
        }
        return str.toTypedArray()
    }

    private fun formatDuration(sec: Int) : String {
        var min = sec / 60
        var sec = sec - min * 60
        return "$min:$sec"
    }
}