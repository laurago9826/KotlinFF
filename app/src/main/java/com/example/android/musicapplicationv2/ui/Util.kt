package com.example.android.musicapplicationv2.ui

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.musicapplicationv2.data.Song

fun formatSong(song: Song, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("\t${song.song_title}<br>")
        append("\t${song.artist}<br>")
        append("\t${song.album}<br>")
        append("\t${song.year}<br>")
        append("\t${song.duration}<br>")
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}