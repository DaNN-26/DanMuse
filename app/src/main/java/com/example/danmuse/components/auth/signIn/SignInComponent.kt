package com.example.danmuse.components.auth.signIn

import com.example.mvi.auth.signIn.SignInIntent

interface SignInComponent {
    fun processIntent(intent: SignInIntent)
}