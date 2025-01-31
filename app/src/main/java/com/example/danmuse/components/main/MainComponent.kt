package com.example.danmuse.components.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.main.home.HomeComponent
import com.example.danmuse.components.main.profile.ProfileComponent
import com.example.danmuse.components.main.songPlayer.SongPlayerComponent
import com.example.danmuse.components.main.vkMusic.VkMusicComponent
import com.example.media.controller.domain.model.SongState
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {
    val stack: Value<ChildStack<*, Child>>

    val songState: StateFlow<SongState>

    fun openPlayer()
    fun navigateBack()
    fun navigateToHome()
    fun navigateToVkMusic()
    fun navigateToProfile()

    sealed interface Child {
        class Home(val component: HomeComponent) : Child
        class VkMusic(val component: VkMusicComponent) : Child
        class Profile(val component: ProfileComponent) : Child
        class SongPlayer(val component: SongPlayerComponent) : Child
    }
}