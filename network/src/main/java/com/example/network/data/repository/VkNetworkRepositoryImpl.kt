package com.example.network.data.repository

import android.util.Log
import com.example.network.core.VkApi
import com.example.network.domain.model.profile.VkProfileResponse
import com.example.network.domain.model.songs.VkSongsResponse
import com.example.network.domain.repository.VkNetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class VkNetworkRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : VkNetworkRepository {
    override suspend fun getVkMusic(
        token: String,
        count: Int,
    ): VkSongsResponse {
        val url = VkApi.SONGS_URL +
                "?v=${VkApi.VERSION}" +
                "&access_token=${token}" +
                "&sig=${VkApi.MD5}" +
                "&count=$count"
        return try {
            val response = client.get(url).body<VkSongsResponse>()
            response
        } catch (e: Exception) {
            Log.e("VkMusicRepositoryImpl", "Error: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getVkProfile(token: String): VkProfileResponse {
        val url = VkApi.PROFILE_URL +
                "?v=${VkApi.VERSION}" +
                "&access_token=${token}" +
                "&fields=photo_400_orig"
        return try {
            val response = client.get(url).body<VkProfileResponse>()
            response
        } catch (e: Exception) {
            Log.e("VkMusicRepositoryImpl", "Error: ${e.message}", e)
            throw e
        }
    }
}