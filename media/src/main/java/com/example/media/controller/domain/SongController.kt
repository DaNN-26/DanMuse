package com.example.media.controller.domain

import com.example.media.controller.domain.model.SongState
import com.example.media.model.Song
import kotlinx.coroutines.flow.StateFlow

interface SongController {
    val songState: StateFlow<SongState>
    fun playSong()
    fun pauseSong()
    fun nextSong()
    fun backSong()
    fun updateSongProgress()
    fun close()
    fun selectMusic(song: Song, songList: List<Song>)
    fun setDuration()
    fun seekTo(position: Long)
}