package com.example.danmuse.components.songbar

import com.arkivanov.decompose.ComponentContext
import com.example.danmuse.media.controller.SongController
import com.example.danmuse.mvi.songBar.SongBarIntent
import javax.inject.Inject

class DefaultSongBarComponent @Inject constructor(
    componentContext: ComponentContext,
    private val controller: SongController
) : SongBarComponent, ComponentContext by componentContext {

    override val state = controller.songState
    override fun processIntent(intent: SongBarIntent) {
        when (intent) {
            is SongBarIntent.Pause -> controller.pauseSong()
            is SongBarIntent.Play -> controller.playSong()
            is SongBarIntent.Next -> controller.nextSong()
            is SongBarIntent.UpdateProgress -> controller.updateSongProgress()
            is SongBarIntent.SetDuration -> controller.setDuration()
            is SongBarIntent.Close -> controller.close()
        }
    }
}