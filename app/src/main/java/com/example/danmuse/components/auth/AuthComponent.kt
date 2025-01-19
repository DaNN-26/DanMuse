package com.example.danmuse.components.auth

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.auth.signIn.SignInComponent
import com.example.danmuse.components.auth.vkHost.VkHostComponent

interface AuthComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class SignIn(val component: SignInComponent) : Child
        class VkHost(val component: VkHostComponent) : Child
    }
}