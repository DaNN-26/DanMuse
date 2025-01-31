package com.example.media.vk

import android.net.Uri
import com.example.media.model.Song
import com.example.media.vk.model.VkStoreState
import com.example.network.domain.model.songs.VkSong
import com.example.util.formatDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object VkStore {
    private val _state = MutableStateFlow(VkStoreState())
    val state = _state.asStateFlow()

    fun updateState(songs: List<VkSong>) {
        _state.update { it.copy(songsList = songs.toSongList()) }
    }

    private fun VkSong.toSong() =
        Song(
            id = this.id,
            name = this.title + " " + (this.subtitle ?: ""),
            artist = this.artist,
            path = this.url,
            duration = this.duration.formatDuration(),
            albumId = if(this.album != null)
                this.album?.id?.toLong()
            else
                null,
            albumArtPath = if(this.album?.thumb != null)
                Uri.parse(this.album?.thumb?.photo1200)
            else
                null,
            isAlbumArtExists = this.album?.thumb != null
        )

    private fun List<VkSong>.toSongList() =
        this.map { it.toSong() }
}