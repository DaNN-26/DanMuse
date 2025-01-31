package com.example.network.domain.model.songs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumb(
    val width: Int = 0,
    val height: Int = 0,
    val id: String,
    @SerialName("photo_34")
    val photo34: String? = null,
    @SerialName("photo_68")
    val photo68: String? = null,
    @SerialName("photo_135")
    val photo135: String? = null,
    @SerialName("photo_270")
    val photo270: String? = null,
    @SerialName("photo_300")
    val photo300: String? = null,
    @SerialName("photo_600")
    val photo600: String? = null,
    @SerialName("photo_1200")
    val photo1200: String? = null
)