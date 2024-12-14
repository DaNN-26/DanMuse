package com.example.danmuse.components.app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.app.AppComponent.Child
import com.example.danmuse.components.app.home.DefaultHomeComponent
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultAppComponent @Inject constructor(
    private val componentContext: ComponentContext
) : AppComponent, ComponentContext by componentContext {

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
        }

    private fun homeComponent(componentContext: ComponentContext) =
        DefaultHomeComponent(componentContext)

    @Serializable
    sealed interface Config {
        @Serializable
        data object Home : Config
        @Serializable
        data object Profile : Config
        @Serializable
        data object Online : Config
    }
}