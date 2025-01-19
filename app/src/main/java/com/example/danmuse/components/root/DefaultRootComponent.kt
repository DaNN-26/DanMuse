package com.example.danmuse.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.auth.DefaultAuthComponent
import com.example.danmuse.components.main.DefaultMainComponent
import com.example.danmuse.components.root.RootComponent.Child
import com.example.media.controller.domain.SongController
import com.example.media.vkStore.VkStore
import com.example.network.domain.repository.VkMusicRepository
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultRootComponent @Inject constructor(
    componentContext: ComponentContext,
    private val controller: SongController,
    private val vkMusicRepository: VkMusicRepository,
    private val vkStore: VkStore
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Auth,
            serializer = Config.serializer(),
            handleBackButton = false,
            childFactory = ::child
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): Child =
        when (config) {
            is Config.Main -> Child.Main(appComponent(componentContext))
            is Config.Auth -> Child.Auth(authComponent(componentContext))
        }

    private fun appComponent(componentContext: ComponentContext) =
        DefaultMainComponent(
            componentContext = componentContext,
            controller = controller,
            vkMusicRepository = vkMusicRepository,
            vkStore = vkStore
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun authComponent(componentContext: ComponentContext) =
        DefaultAuthComponent(
            componentContext = componentContext,
            navigateToMain = { navigation.push(Config.Main) }
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Main : Config
        @Serializable
        data object Auth : Config
    }
}