package com.example.danmuse.components.app.songPlayer

import com.arkivanov.decompose.value.Value
import com.example.danmuse.media.controller.model.SongState
import com.example.danmuse.mvi.app.songPlayer.SongPlayerIntent

interface SongPlayerComponent {
    val state: Value<SongState>

    fun processIntent(intent: SongPlayerIntent)
}