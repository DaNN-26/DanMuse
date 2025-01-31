package com.example.danmuse.ui.main.vkMusic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.danmuse.components.main.vkMusic.VkMusicComponent
import com.example.danmuse.ui.main.components.MusicItem
import com.example.mvi.main.vkMusic.VkMusicIntent

@Composable
fun VkMusic(
    component: VkMusicComponent,
    modifier: Modifier
) {
    val state by component.state.subscribeAsState()
    val vkStoreState by component.vkStoreState.collectAsState()
    val songState by component.songState.collectAsState()

    LaunchedEffect(vkStoreState) {
        component.processIntent(VkMusicIntent.InitializeSongs)
    }

        if(state.isLoading)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                CircularProgressIndicator()
            }
        else
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = modifier.fillMaxSize()
            ) {
                items(
                    if (state.searchQuery.isEmpty())
                        state.songsList
                    else
                        state.filteredSongsList
                ) { song ->
                    MusicItem(
                        song = song,
                        formattedCurrentPosition = songState.formattedCurrentPosition,
                        currentSong = songState.song,
                        onMusicItemClick = { component.processIntent(VkMusicIntent.OnSongSelected(it)) }
                    )
                }
            }
    }