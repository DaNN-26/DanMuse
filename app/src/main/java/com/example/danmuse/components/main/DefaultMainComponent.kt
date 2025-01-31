package com.example.danmuse.components.main

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.main.MainComponent.Child
import com.example.danmuse.components.main.home.DefaultHomeComponent
import com.example.danmuse.components.main.profile.DefaultProfileComponent
import com.example.danmuse.components.main.songPlayer.DefaultSongPlayerComponent
import com.example.danmuse.components.main.vkMusic.DefaultVkMusicComponent
import com.example.keystore.KeystoreManager
import com.example.media.controller.domain.SongController
import com.example.media.vk.VkStore
import com.example.network.domain.repository.VkNetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultMainComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val controller: SongController,
    private val vkNetworkRepository: VkNetworkRepository,
    private val vkStore: VkStore,
    private val keystoreManager: KeystoreManager,
    private val navigateToAuth: () -> Unit
) : MainComponent, ComponentContext by componentContext {

    override val songState = controller.songState

    private val navigation = StackNavigation<Config>()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        initializeVkSongs()
    }

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
            is Config.VkMusic -> Child.VkMusic(vkMusicComponent(componentContext))
            is Config.Profile -> Child.Profile(profileComponent(componentContext))
            is Config.SongPlayer -> Child.SongPlayer(songPlayerComponent(componentContext))
        }

    private fun homeComponent(componentContext: ComponentContext) =
        DefaultHomeComponent(componentContext, controller)

    private fun vkMusicComponent(componentContext: ComponentContext) =
        DefaultVkMusicComponent(
            componentContext = componentContext,
            controller = controller,
            vkStore = vkStore
        )

    private fun profileComponent(componentContext: ComponentContext) =
        DefaultProfileComponent(
            componentContext = componentContext,
            vkNetworkRepository = vkNetworkRepository,
            vkStore = vkStore,
            keystoreManager = keystoreManager,
            signOut = { signOut() }
        )

    private fun songPlayerComponent(componentContext: ComponentContext) =
        DefaultSongPlayerComponent(componentContext, controller)

    private fun signOut() {
        keystoreManager.saveToken("")
        controller.close()
        navigateToAuth()
    }

    override fun navigateToHome() {
        navigation.replaceAll(Config.Home)
    }

    override fun navigateToVkMusic() {
        navigation.replaceAll(Config.VkMusic)
    }

    override fun navigateToProfile() {
        navigation.replaceAll(Config.Profile)
    }

    override fun navigateBack() {
        navigation.pop()
    }

    @OptIn(DelicateDecomposeApi::class)
    override fun openPlayer() {
        navigation.push(Config.SongPlayer)
    }

    private fun initializeVkSongs() {
        scope.launch {
            try {
                val token = keystoreManager.getToken()
                val response = vkNetworkRepository.getVkMusic(
                    token ?: "",
                    6000
                ).response
                vkStore.updateState(songs = response.items ?: emptyList())
            } catch (e: Exception) {
                Log.d("VkMusicComponent error", e.message.toString())
            }
        }
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Home : Config
        @Serializable
        data object VkMusic : Config
        @Serializable
        data object Profile : Config
        @Serializable
        data object SongPlayer : Config
    }
}