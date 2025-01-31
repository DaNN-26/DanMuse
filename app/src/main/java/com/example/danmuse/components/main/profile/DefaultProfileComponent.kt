package com.example.danmuse.components.main.profile

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.keystore.KeystoreManager
import com.example.media.vk.VkStore
import com.example.mvi.main.profile.ProfileIntent
import com.example.mvi.main.profile.ProfileState
import com.example.network.domain.repository.VkNetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultProfileComponent @Inject constructor(
    componentContext: ComponentContext,
    private val vkNetworkRepository: VkNetworkRepository,
    private val vkStore: VkStore,
    private val keystoreManager: KeystoreManager,
    private val signOut: () -> Unit
) : ProfileComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(PROFILE_COMPONENT, ProfileState.serializer()) ?: ProfileState()
    )
    override val state = _state

    override val vkStoreState = vkStore.state

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.InitializeProfile -> initializeProfile()
            is ProfileIntent.SignOut -> signOut()
        }
    }

    private fun initializeProfile() {
        scope.launch {
            try {
                val token = keystoreManager.getToken()
                val profile = vkNetworkRepository.getVkProfile(token ?: "").response[0]
                _state.update { it.copy(
                    profileImage = profile.photo,
                    profileName = profile.firstName + " " + profile.lastName,
                    songsCount = vkStore.state.value.songsList.size,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                Log.d("DefaultProfileComponent", e.message.toString())
            }
        }
    }

    companion object {
        const val PROFILE_COMPONENT = "PROFILE_COMPONENT"
    }
}