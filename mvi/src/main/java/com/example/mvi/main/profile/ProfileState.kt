package com.example.mvi.main.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileState(
    val profileImage: String = "",
    val profileName: String = "",
    val songsCount: Int = 0,
    val isLoading: Boolean = true
)
