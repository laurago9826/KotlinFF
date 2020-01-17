package com.example.android.musicapplicationv2.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.android.musicapplicationv2.R
import com.example.android.musicapplicationv2.databinding.AddSongFragmentBinding
import com.example.android.musicapplicationv2.databinding.ListMusicFragmentBinding
import com.example.android.musicapplicationv2.ui.add.AddSongFragmentArgs
import com.example.android.musicapplicationv2.ui.list.ListMusicViewModel
import kotlinx.android.synthetic.main.list_music_fragment.view.*


class ListMusicFragment : Fragment() {

    private lateinit var listMusicViewModel: ListMusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ListMusicFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.list_music_fragment, container, false
        )

        val argsFromAdd  by navArgs<AddSongFragmentArgs>()



        val test = ArrayList<String>()
        test.add("test1")
        test.add("test2")
        test.add("test3")

        val view: View = inflater.inflate(R.layout.list_music_fragment, container, false)
        var listV = binding.root.findViewById(R.id.listview_1) as ListView
        listV.adapter = ArrayAdapter(activity!!.applicationContext, R.layout.song_templ, test)


        return binding.root
    }
}
