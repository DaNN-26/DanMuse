package com.example.network.domain.model.songs

import kotlinx.serialization.Serializable

@Serializable
data class VkSongsResponse(
    val response: SongsResponseData
)
