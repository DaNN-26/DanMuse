package com.example.mvi.auth.signIn

sealed class SignInIntent {
    data object Login : SignInIntent()
}