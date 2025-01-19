package com.example.danmuse.components.main.songPlayer

import com.example.media.controller.domain.model.SongState
import com.example.mvi.main.songPlayer.SongPlayerIntent
import kotlinx.coroutines.flow.StateFlow

interface SongPlayerComponent {
    val songState: StateFlow<SongState>

    fun processIntent(intent: SongPlayerIntent)
}