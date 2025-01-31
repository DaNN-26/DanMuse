package com.example.danmuse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.media3.exoplayer.ExoPlayer
import com.arkivanov.decompose.defaultComponentContext
import com.example.danmuse.components.root.DefaultRootComponent
import com.example.danmuse.components.songbar.DefaultSongBarComponent
import com.example.danmuse.ui.root.Root
import com.example.danmuse.ui.theme.DanMuseTheme
import com.example.keystore.KeystoreManager
import com.example.media.controller.domain.SongController
import com.example.media.vk.VkStore
import com.example.network.domain.repository.VkNetworkRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var player: ExoPlayer
    @Inject
    lateinit var controller: SongController
    @Inject
    lateinit var vkNetworkRepository: VkNetworkRepository
    @Inject
    lateinit var vkStore: VkStore
    @Inject
    lateinit var keystoreManager: KeystoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val componentContext = defaultComponentContext()

        val rootComponent = DefaultRootComponent(
            componentContext = componentContext,
            controller = controller,
            vkNetworkRepository = vkNetworkRepository,
            vkStore = vkStore,
            keystoreManager = keystoreManager
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

        player.stop()
        player.release()
    }
}