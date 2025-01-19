package com.example.network.data.repository

import android.util.Log
import com.example.network.core.ApiConst
import com.example.network.core.VkMusicArgs
import com.example.network.domain.model.VkResponse
import com.example.network.domain.repository.VkMusicRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class VkMusicRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : VkMusicRepository {
    override suspend fun getVkMusic(count: Int): VkResponse {
        val url = ApiConst.URL +
                "?v=${VkMusicArgs.VERSION}" +
                "&access_token=${VkMusicArgs.token}" +
                "&sig=${VkMusicArgs.MD5}" +
                "&count=$count"
        return try {
            Log.d("VkMusicRepositoryImpl", "Url: $url")
            val response = client.get(url).body<VkResponse>()
            Log.d("VkMusicRepositoryImpl", "Response: $response")
            response
        } catch (e: Exception) {
            Log.e("VkMusicRepositoryImpl", "Error: ${e.message}", e)
            throw e
        }
    }
}