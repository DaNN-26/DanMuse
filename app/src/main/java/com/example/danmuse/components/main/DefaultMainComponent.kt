package com.example.danmuse.components.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.main.MainComponent.Child
import com.example.danmuse.components.main.home.DefaultHomeComponent
import com.example.danmuse.components.main.songPlayer.DefaultSongPlayerComponent
import com.example.media.controller.domain.SongController
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultMainComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val controller: SongController
) : MainComponent, ComponentContext by componentContext {

    override val songState = controller.songState

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Home,
            serializer = Config.serializer(),
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): Child =
        when (config) {
            is Config.Home -> Child.Home(homeComponent(componentContext))
            is Config.Profile -> Child.Profile()
            is Config.Online -> Child.Online()
            is Config.SongPlayer -> Child.SongPlayer(songPlayerComponent(componentContext))
        }

    private fun homeComponent(componentContext: ComponentContext) =
        DefaultHomeComponent(componentContext, controller)

    private fun songPlayerComponent(componentContext: ComponentContext) =
        DefaultSongPlayerComponent(componentContext, controller)

    @OptIn(DelicateDecomposeApi::class)
    override fun openPlayer() {
        navigation.push(Config.SongPlayer)
    }

    override fun navigateBack() {
        navigation.pop()
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Home : Config
        @Serializable
        data object Profile : Config
        @Serializable
        data object Online : Config
        @Serializable
        data object SongPlayer : Config
    }
}