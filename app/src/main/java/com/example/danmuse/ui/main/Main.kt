package com.example.danmuse.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.danmuse.components.main.MainComponent
import com.example.danmuse.components.main.MainComponent.Child
import com.example.danmuse.components.songbar.SongBarComponent
import com.example.danmuse.ui.components.navbar.BottomNavBar
import com.example.danmuse.ui.components.songBar.SongBar
import com.example.danmuse.ui.components.topbar.TopBar
import com.example.danmuse.ui.main.home.Home
import com.example.danmuse.ui.main.profile.Profile
import com.example.danmuse.ui.main.songPlayer.SongPlayer
import com.example.danmuse.ui.main.vkMusic.VkMusic
import com.example.mvi.main.home.HomeIntent
import com.example.mvi.main.vkMusic.VkMusicIntent

@Composable
fun Main(
    component: MainComponent,
    songBarComponent: SongBarComponent
) {
    val stack = component.stack
    val stackState by stack.subscribeAsState()

    val songState by component.songState.collectAsState()

    val isSongBarVisible by rememberSaveable(songState.song, stackState.active.instance) {
        mutableStateOf(songState.song != null && stackState.active.instance !is Child.SongPlayer)
    }

    Scaffold(
        topBar = {
            when (val instance = stackState.active.instance) {
                is Child.Home ->
                    TopBar(
                        isSearching = true,
                        query = instance.component.state.subscribeAsState().value.searchQuery,
                        onQueryChange = { newQuery ->
                            instance.component.processIntent(HomeIntent.OnSearchQueryChange(newQuery))
                        },
                        clearQuery = { instance.component.processIntent(HomeIntent.ClearSearchQuery) },
                    )
                is Child.VkMusic ->
                    TopBar(
                        isSearching = true,
                        query = instance.component.state.subscribeAsState().value.searchQuery,
                        onQueryChange = { newQuery ->
                            instance.component.processIntent(VkMusicIntent.OnSearchQueryChange(newQuery))
                        },
                        clearQuery = { instance.component.processIntent(VkMusicIntent.ClearSearchQuery) },
                    )
                is Child.Profile ->
                    TopBar()
                is Child.SongPlayer ->
                    TopBar(
                        canNavigateBack = true,
                        navigateBack = component::navigateBack
                    )
            }
        },
        bottomBar = {
            Column {
                AnimatedVisibility(isSongBarVisible) {
                    SongBar(
                        component = songBarComponent,
                        openPlayer = component::openPlayer
                    )
                }
                BottomNavBar(
                    isBottomNavBarVisible = stackState.active.instance !is Child.SongPlayer,
                    navigateToHome = component::navigateToHome,
                    navigateToVkMusic = component::navigateToVkMusic,
                    navigateToProfile = component::navigateToProfile
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Children(
            stack = stack,
            animation = stackAnimation(fade() + scale())
        ) { child ->
            when (val instance = child.instance) {
                is Child.Home -> Home(
                    instance.component,
                    modifier = Modifier.padding(paddingValues)
                )
                is Child.Profile -> Profile(
                    instance.component,
                    modifier = Modifier.padding(paddingValues)
                )
                is Child.VkMusic -> VkMusic(
                    instance.component,
                    modifier = Modifier.padding(paddingValues)
                )
                is Child.SongPlayer -> SongPlayer(instance.component)
            }
        }     
    }
}