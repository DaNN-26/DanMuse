package com.example.network.domain.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class VkProfileResponse(
    val response: List<VkProfile>
)
