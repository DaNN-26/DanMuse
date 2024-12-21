package com.example.danmuse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.media3.common.util.UnstableApi
import com.arkivanov.decompose.defaultComponentContext
import com.example.danmuse.components.root.DefaultRootComponent
import com.example.danmuse.components.songbar.DefaultSongBarComponent
import com.example.danmuse.media.controller.SongController
import com.example.danmuse.media.di.MediaModule
import com.example.danmuse.ui.root.Root
import com.example.danmuse.ui.theme.DanMuseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val componentContext = defaultComponentContext()
        val controller = SongController(MediaModule.provideMediaPlayer(this))

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
                    songBarComponent = songBarComponent,
                    controller = controller
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