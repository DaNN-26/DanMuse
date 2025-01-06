package com.example.danmuse.components.main.songPlayer

import com.arkivanov.decompose.ComponentContext
import com.example.media.controller.domain.SongController
import com.example.mvi.app.songPlayer.SongPlayerIntent
import javax.inject.Inject

class DefaultSongPlayerComponent @Inject constructor(
    componentContext: ComponentContext,
    private val controller: SongController
) : SongPlayerComponent, ComponentContext by componentContext {

    override val songState = controller.songState

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