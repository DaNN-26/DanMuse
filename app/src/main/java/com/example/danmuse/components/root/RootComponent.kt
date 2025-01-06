package com.example.danmuse.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.danmuse.components.main.MainComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Main(val component: MainComponent) : Child
    }
}