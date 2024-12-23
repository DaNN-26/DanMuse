package com.example.danmuse.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.app.DefaultAppComponent
import com.example.danmuse.components.root.RootComponent.Child
import com.example.danmuse.media.controller.SongController
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultRootComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val controller: SongController
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.App,
            serializer = Config.serializer(),
            handleBackButton = false,
            childFactory = ::child
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): Child =
        when (config) {
            is Config.App -> Child.App(appComponent(componentContext))
        }

    private fun appComponent(componentContext: ComponentContext) =
        DefaultAppComponent(componentContext, controller)

    @Serializable
    private sealed interface Config {
        @Serializable
        data object App : Config
    }
}