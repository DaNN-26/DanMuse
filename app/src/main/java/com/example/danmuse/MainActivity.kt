package com.example.danmuse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.example.danmuse.components.root.DefaultRootComponent
import com.example.danmuse.components.songbar.DefaultSongBarComponent
import com.example.danmuse.media.di.MediaModule
import com.example.danmuse.ui.root.Root
import com.example.danmuse.ui.theme.DanMuseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val componentContext = defaultComponentContext()

        val mediaPlayer = MediaModule.provideMediaPlayer(this)
        val controller = MediaModule.provideSongController(mediaPlayer)

        val rootComponent = DefaultRootComponent(
            componentContext = componentContext,
            controller = controller
        )
        val songBarComponent = DefaultSongBarComponent(
            componentContext = componentContext,
            controller = controller
        )

        setContent {
            DanMuseTheme {
                Root(
                    component = rootComponent,
                    songBarComponent = songBarComponent
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val mediaPlayer = MediaModule.provideMediaPlayer(this)
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}