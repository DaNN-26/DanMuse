package com.example.danmuse.components.songbar

import com.arkivanov.decompose.value.Value
import com.example.danmuse.media.controller.model.SongState
import com.example.danmuse.mvi.songBar.SongBarIntent

interface SongBarComponent {
    val state: Value<SongState>
    fun processIntent(intent: SongBarIntent)
}