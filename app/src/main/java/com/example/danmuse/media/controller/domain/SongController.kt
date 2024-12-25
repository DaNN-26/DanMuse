package com.example.danmuse.media.controller.domain

import com.arkivanov.decompose.value.Value
import com.example.danmuse.media.controller.model.SongState
import com.example.danmuse.media.model.Song

interface SongController {
    val songState: Value<SongState>
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