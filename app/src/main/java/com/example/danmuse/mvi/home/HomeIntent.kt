package com.example.danmuse.mvi.home

import android.content.Context

sealed class HomeIntent {
    class InitializeSongs(val context: Context) : HomeIntent()
    class OnSearchQueryChange(val searchQuery: String) : HomeIntent()
    data object ClearSearchQuery : HomeIntent()
}