package com.example.danmuse.media.controller.data

import android.content.Context
import android.content.Intent
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.danmuse.media.controller.domain.SongController
import com.example.danmuse.media.controller.model.SongState
import com.example.danmuse.media.model.Song
import com.example.danmuse.media.service.SongService
import com.example.danmuse.util.formatDuration
import javax.inject.Inject

class SongControllerImpl @Inject constructor(
    private val mediaPlayer: ExoPlayer,
    private val context: Context
): SongController {

    val intent = Intent(context, SongService::class.java)

    private val _songState = MutableValue(SongState())

    override val songState = _songState

    init {
        _songState.update { it.copy(
            player = mediaPlayer
        ) }
    }

    private fun playNewSong() {
        mediaPlayer.clearMediaItems()

        val mediaItem = MediaItem.Builder()
            .setUri(songState.value.song?.path!!)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(songState.value.song?.name)
                    .setArtist(songState.value.song?.artist)
                    .setArtworkUri(songState.value.song?.albumArtPath)
                    .build()
            )
            .build()
        mediaPlayer.setMediaItem(mediaItem)

        mediaPlayer.prepare()
        mediaPlayer.play()
        _songState.update { it.copy(
            isPaused = false,
            trackIndex = getTrackIndex()
        ) }
        context.startService(intent)
    }

    override fun playSong() {
        if(songState.value.isPaused && songState.value.currentPosition <= songState.value.duration) {
            mediaPlayer.play()
            _songState.update { it.copy(isPaused = false) }
        }
    }

    override fun pauseSong() {
        mediaPlayer.pause()
        _songState.update { it.copy(isPaused = true) }
    }

    override fun nextSong() {
        if(songState.value.trackIndex != songState.value.songList.lastIndex) {
            _songState.update { it.copy(
                song = songState.value.songList[songState.value.trackIndex + 1],
                currentPosition = 0
            ) }
            playNewSong()
        }
    }

    override fun backSong() {
        if(songState.value.trackIndex != 0 && songState.value.currentPosition < 5000L) {
            _songState.update { it.copy(
                song = songState.value.songList[songState.value.trackIndex - 1],
                currentPosition = 0
            ) }
            playNewSong()
        } else {
            _songState.update { it.copy(
                currentPosition = 0
            ) }
            mediaPlayer.seekTo(0)
        }
    }

    override fun updateSongProgress() {
        _songState.update { it.copy(
            currentPosition = mediaPlayer.currentPosition,
            formattedCurrentPosition = mediaPlayer.currentPosition.formatDuration()
        ) }
    }

    override fun close() {
        _songState.update { it.copy(
            song = null,
            isPaused = false,
            currentPosition = 0,
            formattedCurrentPosition = "00:00",
            duration = 0,
            trackIndex = 0
        ) }
        _songState.update { it.copy(song = null) }

        mediaPlayer.stop()
    }

    override fun selectMusic(
        song: Song,
        songList: List<Song>
    ) {
        _songState.update { it.copy(
            song = song,
            songList = songList,
            formattedCurrentPosition = "00:00",
            currentPosition = 0
        ) }
        playNewSong()
    }

    override fun setDuration() {
        _songState.update {
            it.copy(duration = mediaPlayer.duration.coerceAtLeast(0L))
        }
    }

    override fun seekTo(position: Long) {
        _songState.update { it.copy(currentPosition = position) }
        mediaPlayer.seekTo(position)
    }

    private fun getTrackIndex() =
        songState.value.songList.indexOf(songState.value.song)
}