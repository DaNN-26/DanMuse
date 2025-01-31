package com.example.mvi.main.profile

sealed class ProfileIntent {
    data object InitializeProfile : ProfileIntent()
    data object SignOut : ProfileIntent()
}