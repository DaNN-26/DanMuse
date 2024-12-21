package com.example.danmuse.mvi.app.home

import android.content.Context
import com.example.danmuse.media.model.Song

sealed class HomeIntent {
    class InitializeSongs(val context: Context) : HomeIntent()
    class OnSearchQueryChange(val searchQuery: String) : HomeIntent()
    data object ClearSearchQuery : HomeIntent()
    class OnSongSelected(val song: Song) : HomeIntent()
}