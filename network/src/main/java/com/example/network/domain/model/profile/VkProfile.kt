package com.example.network.domain.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VkProfile(
    val id: Int,
    @SerialName("photo_400_orig")
    val photo: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("can_access_closed")
    val canAccessClosed: Boolean,
    @SerialName("is_closed")
    val isClosed: Boolean
)
