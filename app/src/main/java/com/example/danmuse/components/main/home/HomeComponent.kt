package com.example.danmuse.components.main.home

import com.arkivanov.decompose.value.Value
import com.example.media.controller.domain.model.SongState
import com.example.mvi.app.home.HomeIntent
import com.example.mvi.app.home.HomeState
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {
    val state: Value<HomeState>

    val songState: StateFlow<SongState>

    fun processIntent(intent: HomeIntent)
}