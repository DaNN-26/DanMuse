package com.example.danmuse.components.main.vkMusic

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.media.controller.domain.SongController
import com.example.media.vkStore.VkStore
import com.example.mvi.main.vkMusic.VkMusicIntent
import com.example.mvi.main.vkMusic.VkMusicState
import javax.inject.Inject

class DefaultVkMusicComponent @Inject constructor(
    componentContext: ComponentContext,
    private val controller: SongController,
    private val vkStore: VkStore
) : VkMusicComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(VKMUSIC_COMPONENT, VkMusicState.serializer()) ?: VkMusicState()
    )

    override val state = _state

    override val vkStoreState = vkStore.state


    override val songState = controller.songState

    override fun processIntent(intent: VkMusicIntent) {
        when (intent) {
            is VkMusicIntent.InitializeSongs -> _state.update { it.copy(songsList = vkStore.state.value.songsList) }
            is VkMusicIntent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = intent.searchQuery) }
                getTracksBySearchQuery()
            }
            is VkMusicIntent.ClearSearchQuery -> _state.update { it.copy(searchQuery = "") }
            is VkMusicIntent.OnSongSelected -> controller.selectMusic(intent.song, state.value.songsList)
        }
    }

    private fun getTracksBySearchQuery() {
        _state.update { state ->
            val normalizedQueryParts = state.searchQuery
                .replace(",", "")
                .replace("  ", " ")
                .trim()
                .lowercase()
                .split(" ")

            state.copy(
                filteredSongsList = state.songsList.filter { track ->
                    val normalizedTrackName = track.name
                        .replace(",", "")
                        .replace("  ", " ")
                        .trim()
                        .lowercase()
                    val normalizedArtistName = track.artist
                        ?.replace(",", "")
                        ?.replace("  ", " ")
                        ?.trim()
                        ?.lowercase()

                    normalizedQueryParts.all { queryPart ->
                        normalizedTrackName.contains(queryPart) || (normalizedArtistName?.contains(queryPart) == true)
                    }
                }
            )
        }
    }

    companion object {
        const val VKMUSIC_COMPONENT = "VKMUSIC_COMPONENT"
    }
}