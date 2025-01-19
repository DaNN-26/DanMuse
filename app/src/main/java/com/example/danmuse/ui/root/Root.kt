package com.example.danmuse.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.danmuse.components.root.RootComponent
import com.example.danmuse.components.root.RootComponent.Child
import com.example.danmuse.components.songbar.SongBarComponent
import com.example.danmuse.ui.auth.Auth
import com.example.danmuse.ui.main.Main

@Composable
fun Root(
    component: RootComponent,
    songBarComponent: SongBarComponent
) {
    val stack = component.stack
    Children(
        stack = stack,
        animation = stackAnimation(fade() + scale())
    ) { child ->
        when (val instance = child.instance) {
            is Child.Main ->
                Main(
                    component = instance.component,
                    songBarComponent = songBarComponent
                )
            is Child.Auth -> Auth(component = instance.component)
        }
    }
}