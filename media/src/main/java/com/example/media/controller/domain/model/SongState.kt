package com.example.media.controller.domain.model

import androidx.media3.exoplayer.ExoPlayer
import com.example.media.model.Song

data class SongState(
    val player: ExoPlayer? = null,
    val song: Song? = null,
    val songList: List<Song> = emptyList(),
    val isPaused: Boolean = false,
    val currentPosition: Long = 0L,
    val formattedCurrentPosition: String = "00:00",
    val duration: Long = 0L,
    val trackIndex: Int = 0
)
