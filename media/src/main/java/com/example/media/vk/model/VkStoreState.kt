package com.example.media.vk.model

import com.example.media.model.Song

data class VkStoreState(
    val songsList: List<Song> = emptyList(),
)
