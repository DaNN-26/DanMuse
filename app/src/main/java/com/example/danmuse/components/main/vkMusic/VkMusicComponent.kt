package com.example.danmuse.components.main.vkMusic

import com.arkivanov.decompose.value.Value
import com.example.media.controller.domain.model.SongState
import com.example.media.vkStore.model.VkStoreState
import com.example.mvi.main.vkMusic.VkMusicIntent
import com.example.mvi.main.vkMusic.VkMusicState
import kotlinx.coroutines.flow.StateFlow

interface VkMusicComponent {
    val state: Value<VkMusicState>

    val vkStoreState: StateFlow<VkStoreState>

    val songState: StateFlow<SongState>

    fun processIntent(intent: VkMusicIntent)
}