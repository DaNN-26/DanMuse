package com.example.danmuse.media.controller

import com.example.danmuse.media.model.Song

interface SongEvent {
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