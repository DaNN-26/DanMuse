package com.example.danmuse.ui.auth

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.example.danmuse.components.auth.AuthComponent
import com.example.danmuse.components.auth.AuthComponent.Child
import com.example.danmuse.ui.auth.signIn.SignIn
import com.example.danmuse.ui.auth.vkHost.VkHost

@Composable
fun Auth(
    component: AuthComponent
) {
    val stack = component.stack
    Surface {
        Children(stack = stack) { child ->
            when (val instance = child.instance) {
                is Child.SignIn -> SignIn(instance.component)
                is Child.VkHost -> VkHost(instance.component)
            }
        }
    }
}