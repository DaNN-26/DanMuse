package com.example.danmuse.components.app.home

import com.arkivanov.decompose.value.Value
import com.example.danmuse.media.controller.model.SongState
import com.example.danmuse.mvi.app.home.HomeIntent
import com.example.danmuse.mvi.app.home.HomeState

interface HomeComponent {
    val state: Value<HomeState>

    val songState: Value<SongState>

    fun processIntent(intent: HomeIntent)
}