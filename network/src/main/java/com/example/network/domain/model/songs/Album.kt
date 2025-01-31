package com.example.network.domain.model.songs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: Int,
    val title: String,
    @SerialName("owner_id")
    val ownerId: Int,
    @SerialName("access_key")
    val accessKey: String,
    val thumb: Thumb? = null,
    @SerialName("main_color")
    val mainColor: String? = null
)