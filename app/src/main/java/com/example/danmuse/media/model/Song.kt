package com.example.danmuse.media.model

import android.net.Uri
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: Long,
    val name: String,
    val artist: String? = null,
    val path: String,
    val duration: String,
    val albumId: Long?,
    @Contextual
    val albumArtPath: Uri?,
    val isAlbumArtExists: Boolean
)
