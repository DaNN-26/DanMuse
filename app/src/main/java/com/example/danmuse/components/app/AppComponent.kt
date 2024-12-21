package com.example.danmuse.components.app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.app.home.HomeComponent
import com.example.danmuse.components.app.songPlayer.SongPlayerComponent
import com.example.danmuse.media.controller.SongState

interface AppComponent {
    val stack: Value<ChildStack<*, Child>>

    val songState: Value<SongState>

    fun openPlayer()

    sealed interface Child {
        class Home(val component: HomeComponent) : Child
        class Profile() : Child
        class Online() : Child
        class SongPlayer(val component: SongPlayerComponent) : Child
    }
}