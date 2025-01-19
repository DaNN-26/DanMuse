package com.example.network.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VkResponse(
    val response: ResponseData
)

@Serializable
data class ResponseData(
    val count: Int = 0,
    val items: List<VkSong>? = null
)

@Serializable
data class VkSong(
    val artist: String,
    val id: Long,
    @SerialName("owner_id")
    val ownerId: Long,
    val title: String,
    val duration: Int,
    @SerialName("access_key")
    val accessKey: String,
    @SerialName("is_explicit")
    val isExplicit: Boolean? = null,
    @SerialName("track_code")
    val trackCode: String,
    val url: String,
    val album: Album? = null,
    val subtitle: String? = null,
)

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

@Serializable
data class Thumb(
    @SerialName("width") val width: Int = 0,
    @SerialName("height") val height: Int = 0,
    @SerialName("id") val id: String,
    @SerialName("photo_34") val photo34: String? = null,
    @SerialName("photo_68") val photo68: String? = null,
    @SerialName("photo_135") val photo135: String? = null,
    @SerialName("photo_270") val photo270: String? = null,
    @SerialName("photo_300") val photo300: String? = null,
    @SerialName("photo_600") val photo600: String? = null,
    @SerialName("photo_1200") val photo1200: String? = null
)
