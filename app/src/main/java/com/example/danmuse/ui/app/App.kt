package com.example.danmuse.ui.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.danmuse.components.app.AppComponent
import com.example.danmuse.components.app.AppComponent.Child
import com.example.danmuse.mvi.home.HomeIntent
import com.example.danmuse.ui.app.home.Home
import com.example.danmuse.ui.app.online.Online
import com.example.danmuse.ui.app.profile.Profile
import com.example.danmuse.ui.components.BottomNavBar
import com.example.danmuse.ui.components.BottomSongBar
import com.example.danmuse.ui.components.TopBar

@Composable
fun App(
    component: AppComponent
) {
    val stack = component.stack
    Scaffold(
        topBar = {
            when (val instance = stack.value.active.instance) {
                is Child.Home ->
                    TopBar(
                        isSearching = true,
                        query = instance.component.state.subscribeAsState().value.searchQuery,
                        onQueryChange = { newQuery ->
                            instance.component.processIntent(HomeIntent.OnSearchQueryChange(newQuery))
                        },
                        clearQuery = { instance.component.processIntent(HomeIntent.ClearSearchQuery) },
                    )
                is Child.Online ->
                    TopBar(isSearching = false)
                is Child.Profile ->
                    TopBar(isSearching = false)
            }
        },
        bottomBar = {
            Column {
                BottomSongBar()
                BottomNavBar()
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Children(
            stack = stack,
            animation = stackAnimation(fade() + scale()), 
            modifier = Modifier.padding(paddingValues)
        ) { child ->
            when (val instance = child.instance) {
                is Child.Home -> Home(instance.component)
                is Child.Profile -> Profile()
                is Child.Online -> Online() 
            }
        }     
    }
}