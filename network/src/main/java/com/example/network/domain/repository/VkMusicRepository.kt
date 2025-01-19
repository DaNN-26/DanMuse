package com.example.network.domain.repository

import com.example.network.domain.model.VkResponse

interface VkMusicRepository {
    suspend fun getVkMusic(count: Int): VkResponse
}