package com.example.danmuse

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.media3.exoplayer.ExoPlayer
import com.arkivanov.decompose.defaultComponentContext
import com.example.danmuse.components.root.DefaultRootComponent
import com.example.danmuse.components.songbar.DefaultSongBarComponent
import com.example.danmuse.media.controller.domain.SongController
import com.example.danmuse.media.service.SongService
import com.example.danmuse.ui.root.Root
import com.example.danmuse.ui.theme.DanMuseTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var player: ExoPlayer
    @Inject
    lateinit var controller: SongController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val componentContext = defaultComponentContext()

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

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()

        val intent = Intent(this, SongService::class.java)
        stopService(intent)

        player.stop()
        player.release()
    }
}