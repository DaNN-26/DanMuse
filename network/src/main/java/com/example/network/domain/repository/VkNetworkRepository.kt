package com.example.network.domain.repository

import com.example.network.domain.model.profile.VkProfileResponse
import com.example.network.domain.model.songs.VkSongsResponse

interface VkNetworkRepository {
    suspend fun getVkMusic(token: String, count: Int): VkSongsResponse
    suspend fun getVkProfile(token: String): VkProfileResponse
}