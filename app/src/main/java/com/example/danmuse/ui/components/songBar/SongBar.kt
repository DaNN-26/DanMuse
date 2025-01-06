package com.example.danmuse.ui.components.songBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.example.danmuse.components.songbar.SongBarComponent
import com.example.mvi.songBar.SongBarIntent
import kotlinx.coroutines.delay

@Composable
fun SongBar(
    component: SongBarComponent,
    openPlayer: () -> Unit
) {
    val songState by component.songState.collectAsState()

    DisposableEffect(songState.player) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY || playbackState == Player.STATE_BUFFERING)
                    component.processIntent(com.example.mvi.songBar.SongBarIntent.SetDuration)
            }
        }
        songState.player?.addListener(listener)

        onDispose {
            songState.player?.removeListener(listener)
        }
    }

    LaunchedEffect(songState.player) {
        while (true) {
            component.processIntent(com.example.mvi.songBar.SongBarIntent.UpdateProgress)
            delay(1000L)
            if (songState.currentPosition > songState.duration)
                if (songState.trackIndex != songState.songList.lastIndex)
                    component.processIntent(com.example.mvi.songBar.SongBarIntent.Next)
                else
                    component.processIntent(com.example.mvi.songBar.SongBarIntent.Pause)
        }
    }

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openPlayer() }
            ) {
                SongBarIconButton(
                    onButtonClicked = { component.processIntent(SongBarIntent.Close) },
                    icon = Icons.Default.Close,
                    contentDescription = "button close"
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = songState.song?.name ?: "",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(170.dp)
                    )
                    Text(
                        text = songState.song?.artist ?: "",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
                SongBarIconButton(
                    onButtonClicked = {
                        if (songState.isPaused)
                            component.processIntent(SongBarIntent.Play)
                        else
                            component.processIntent(SongBarIntent.Pause)
                    },
                    icon = if (songState.isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                    contentDescription = "button play/pause"
                )
            }
            LinearProgressIndicator(
                progress = if (songState.duration > 0)
                    songState.currentPosition / songState.duration.toFloat()
                else
                    0f,
                modifier = Modifier.fillMaxWidth()
            )
        }
}

@Composable
fun SongBarIconButton(
    onButtonClicked: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    IconButton(onClick = onButtonClicked) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp)
        )
    }
}