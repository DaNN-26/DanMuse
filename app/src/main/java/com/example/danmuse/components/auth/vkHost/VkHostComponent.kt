package com.example.danmuse.components.auth.vkHost

import com.example.mvi.auth.vkHost.VkHostIntent

interface VkHostComponent {
    fun processIntent(intent: VkHostIntent)
}