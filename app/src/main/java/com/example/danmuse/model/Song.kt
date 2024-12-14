package com.example.danmuse.model

import android.net.Uri
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: Long,
    val name: String,
    val path: String,
    val duration: String,
    val albumId: Long?,
    @Contextual
    val albumArtPath: Uri?,
    val isAlbumArtExists: Boolean
)
