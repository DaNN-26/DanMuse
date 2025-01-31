package com.example.danmuse.components.main.profile

import com.arkivanov.decompose.value.Value
import com.example.media.vk.model.VkStoreState
import com.example.mvi.main.profile.ProfileIntent
import com.example.mvi.main.profile.ProfileState
import kotlinx.coroutines.flow.StateFlow

interface ProfileComponent {
    val state: Value<ProfileState>

    val vkStoreState: StateFlow<VkStoreState>

    fun processIntent(intent: ProfileIntent)
}