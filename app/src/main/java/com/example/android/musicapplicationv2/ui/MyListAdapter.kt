package com.example.android.musicapplicationv2.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.android.musicapplicationv2.R

class MyListAdapter(private val context: Activity, private val title_artist: Array<String?>,
                    private val other_info: Array<String?>)
    : ArrayAdapter<String>(context, R.layout.song_templ, title_artist) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.song_templ, null, true)

        val titleArtistText = rowView.findViewById(R.id.title_and_artist) as TextView
        val otherInfoText = rowView.findViewById(R.id.other_info) as TextView

        titleArtistText.text = title_artist[position]
        otherInfoText.text = other_info[position]

        return rowView
    }
}