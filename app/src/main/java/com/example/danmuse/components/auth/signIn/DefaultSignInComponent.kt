package com.example.danmuse.components.auth.signIn

import com.arkivanov.decompose.ComponentContext
import com.example.mvi.auth.signIn.SignInIntent
import javax.inject.Inject

class DefaultSignInComponent @Inject constructor(
    componentContext: ComponentContext,
    private val navigateToVkHost: () -> Unit
) : SignInComponent, ComponentContext by componentContext {

    override fun processIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.Login -> navigateToVkHost()
        }
    }
}