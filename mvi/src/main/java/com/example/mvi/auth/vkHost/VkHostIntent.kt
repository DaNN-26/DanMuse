package com.example.mvi.auth.vkHost

sealed class VkHostIntent {
    class GetToken(val url: String) : VkHostIntent()
}