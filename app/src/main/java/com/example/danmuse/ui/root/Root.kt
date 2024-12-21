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
import com.example.danmuse.media.controller.SongController
import com.example.danmuse.ui.app.App

@Composable
fun Root(
    component: RootComponent,
    songBarComponent: SongBarComponent,
    controller: SongController
) {
    val stack = component.stack
    Children(
        stack = stack,
        animation = stackAnimation(fade() + scale())
    ) { child ->
        when (val instance = child.instance) {
            is Child.App ->
                App(
                    component = instance.component,
                    songBarComponent = songBarComponent
                )
        }
    }
}