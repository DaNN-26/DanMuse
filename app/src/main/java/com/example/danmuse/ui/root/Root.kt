package com.example.danmuse.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.danmuse.components.root.RootComponent
import com.example.danmuse.components.root.RootComponent.Child
import com.example.danmuse.ui.app.App
import com.example.danmuse.ui.components.TopBar

@Composable
fun Root(
    component: RootComponent
) {
    val stack = component.stack
    Children(
        stack = stack,
        animation = stackAnimation(fade() + scale())
    ) { child ->
        when (val instance = child.instance) {
            is Child.App -> App(instance.component)
        }
    }
}