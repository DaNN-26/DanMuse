package com.example.danmuse.components.app.songPlayer

import com.arkivanov.decompose.ComponentContext
import com.example.danmuse.media.controller.SongController
import com.example.danmuse.mvi.app.songPlayer.SongPlayerIntent
import javax.inject.Inject

class DefaultSongPlayerComponent @Inject constructor(
    componentContext: ComponentContext,
    private val controller: SongController
) : SongPlayerComponent, ComponentContext by componentContext {

    override val state = controller.songState

    override fun processIntent(intent: SongPlayerIntent) {
        when (intent) {
            is SongPlayerIntent.Play -> controller.playSong()
            is SongPlayerIntent.Pause -> controller.pauseSong()
            is SongPlayerIntent.Next -> controller.nextSong()
            is SongPlayerIntent.Back -> controller.backSong()
            is SongPlayerIntent.UpdateProgress -> controller.updateSongProgress()
            is SongPlayerIntent.SetDuration -> controller.setDuration()
            is SongPlayerIntent.Seek -> controller.seekTo(intent.position)
        }
    }
}