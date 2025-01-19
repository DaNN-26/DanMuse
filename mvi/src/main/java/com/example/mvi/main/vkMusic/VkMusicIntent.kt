package com.example.mvi.main.vkMusic

import com.example.media.model.Song

sealed class VkMusicIntent {
    data object InitializeSongs : VkMusicIntent()
    class OnSearchQueryChange(val searchQuery: String) : VkMusicIntent()
    data object ClearSearchQuery : VkMusicIntent()
    class OnSongSelected(val song: Song) : VkMusicIntent()
}