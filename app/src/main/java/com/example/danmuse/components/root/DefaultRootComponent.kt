package com.example.danmuse.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.auth.DefaultAuthComponent
import com.example.danmuse.components.main.DefaultMainComponent
import com.example.danmuse.components.root.RootComponent.Child
import com.example.keystore.KeystoreManager
import com.example.media.controller.domain.SongController
import com.example.media.vk.VkStore
import com.example.network.domain.repository.VkNetworkRepository
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultRootComponent @Inject constructor(
    componentContext: ComponentContext,
    private val controller: SongController,
    private val vkNetworkRepository: VkNetworkRepository,
    private val vkStore: VkStore,
    private val keystoreManager: KeystoreManager
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val isNotAuthorized: Boolean = keystoreManager.getToken()?.isEmpty() ?: true

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = if(isNotAuthorized)
                Config.Auth
            else
                Config.Main,
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
            vkNetworkRepository = vkNetworkRepository,
            vkStore = vkStore,
            keystoreManager = keystoreManager,
            navigateToAuth = { navigation.replaceAll(Config.Auth) }
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun authComponent(componentContext: ComponentContext) =
        DefaultAuthComponent(
            componentContext = componentContext,
            navigateToMain = { navigation.push(Config.Main) },
            keystoreManager = keystoreManager
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Main : Config
        @Serializable
        data object Auth : Config
    }
}