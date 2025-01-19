package com.example.mvi.main.home

import com.example.media.model.Song
import kotlinx.serialization.Serializable

@Serializable
data class HomeState(
    val songsList: List<Song> = emptyList(),
    val filteredSongsList: List<Song> = emptyList(),
    val searchQuery: String = ""
)