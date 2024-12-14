package com.example.danmuse.ui.app.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.danmuse.components.app.home.HomeComponent
import com.example.danmuse.mvi.home.HomeIntent
import com.example.danmuse.ui.app.home.components.MusicItem

@Composable
fun Home(
    component: HomeComponent
) {
    val state by component.state.subscribeAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        component.processIntent(HomeIntent.InitializeSongs(context))
    } //TODO СДЕЛАТЬ КНОПКУ ПЕРЕЗАГРУЗКИ МУЗЫКИ

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            if(state.searchQuery.isEmpty())
                state.songsList
            else
                state.filteredSongsList
        ) { song ->
            MusicItem(song)
        }
    }
}