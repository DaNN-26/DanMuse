package com.example.danmuse.mvi.app.home

import com.example.danmuse.media.model.Song
import kotlinx.serialization.Serializable

@Serializable
data class HomeState(
    val songsList: List<Song> = emptyList(),
    val filteredSongsList: List<Song> = emptyList(),
    val searchQuery: String = ""
)