package com.example.network.domain.model.songs

import kotlinx.serialization.Serializable

@Serializable
data class SongsResponseData(
    val count: Int = 0,
    val items: List<VkSong>? = null
)