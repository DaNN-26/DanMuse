package com.example.danmuse.components.auth.vkHost

import com.arkivanov.decompose.ComponentContext
import com.example.mvi.auth.vkHost.VkHostIntent
import javax.inject.Inject

class DefaultVkHostComponent @Inject constructor(
    componentContext: ComponentContext,
    private val onTokenReceived: (String) -> Unit
) : VkHostComponent, ComponentContext by componentContext {

    override fun processIntent(intent: VkHostIntent) {
        when (intent) {
            is VkHostIntent.GetToken -> getVkToken(intent.url)
        }
    }
    private fun getVkToken(url: String) {
        val token = extractTokenFromUrl(url)
        onTokenReceived(token)
    }
    private fun extractTokenFromUrl(url: String): String {
        val regex = "access_token=([^&]+)".toRegex()
        return regex.find(url)?.groupValues?.get(1) ?: ""
    }
}