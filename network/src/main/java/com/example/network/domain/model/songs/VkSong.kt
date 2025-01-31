package com.example.network.domain.model.songs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VkSong(
    val artist: String,
    val id: Long,
    @SerialName("owner_id")
    val ownerId: Long,
    val title: String,
    val duration: Int,
    @SerialName("access_key")
    val accessKey: String,
    @SerialName("is_explicit")
    val isExplicit: Boolean? = null,
    @SerialName("track_code")
    val trackCode: String,
    val url: String,
    val album: Album? = null,
    val subtitle: String? = null,
)