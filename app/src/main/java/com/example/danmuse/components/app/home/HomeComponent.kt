package com.example.danmuse.components.app.home

import com.arkivanov.decompose.value.Value
import com.example.danmuse.mvi.home.HomeIntent
import com.example.danmuse.mvi.home.HomeState

interface HomeComponent {
    val state: Value<HomeState>

    fun processIntent(intent: HomeIntent)
}