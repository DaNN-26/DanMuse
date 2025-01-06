package com.example.danmuse.components.songbar

import com.example.media.controller.domain.model.SongState
import com.example.mvi.songBar.SongBarIntent
import kotlinx.coroutines.flow.StateFlow

interface SongBarComponent {
    val songState: StateFlow<SongState>
    fun processIntent(intent: SongBarIntent)
}