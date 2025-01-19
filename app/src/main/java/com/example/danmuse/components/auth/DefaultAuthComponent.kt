package com.example.danmuse.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.auth.AuthComponent.Child
import com.example.danmuse.components.auth.signIn.DefaultSignInComponent
import com.example.danmuse.components.auth.vkHost.DefaultVkHostComponent
import com.example.network.core.VkMusicArgs
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultAuthComponent @Inject constructor(
    componentContext: ComponentContext,
    private val navigateToMain: () -> Unit
) : AuthComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.SignIn,
            serializer = Config.serializer(),
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            Config.SignIn -> Child.SignIn(signInComponent(componentContext))
            Config.VkHost -> Child.VkHost(vkHostComponent(componentContext))
        }

    @OptIn(DelicateDecomposeApi::class)
    private fun signInComponent(componentContext: ComponentContext) =
        DefaultSignInComponent(
            componentContext = componentContext,
            navigateToVkHost = { navigation.push(Config.VkHost) }
        )

    private fun vkHostComponent(componentContext: ComponentContext) =
        DefaultVkHostComponent(
            componentContext = componentContext,
            onTokenReceived = { token ->
                VkMusicArgs.token = token
                navigateToMain()
            }
        )

    @Serializable
    sealed interface Config {
        @Serializable
        data object SignIn : Config
        @Serializable
        data object VkHost : Config
    }
}