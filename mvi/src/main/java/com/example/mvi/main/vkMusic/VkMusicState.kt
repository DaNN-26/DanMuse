package com.example.mvi.main.vkMusic

import com.example.media.model.Song
import kotlinx.serialization.Serializable

@Serializable
data class VkMusicState(
    val songsList: List<Song> = emptyList(),
    val filteredSongsList: List<Song> = emptyList(),
    val searchQuery: String = ""
)