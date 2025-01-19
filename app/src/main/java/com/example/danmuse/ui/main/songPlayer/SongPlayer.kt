package com.example.danmuse.ui.main.songPlayer

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import coil3.compose.AsyncImage
import com.example.danmuse.R
import com.example.danmuse.components.main.songPlayer.SongPlayerComponent
import kotlinx.coroutines.delay

@Composable
fun SongPlayer(
    component: SongPlayerComponent
) {
    val state by component.songState.collectAsState()

    val animatedImage by animateDpAsState(targetValue = if(state.isPaused) 20.dp else 0.dp, label = "")

    DisposableEffect(state.player) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY || playbackState == Player.STATE_BUFFERING)
                    component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.SetDuration)
            }
        }
        state.player?.addListener(listener)

        onDispose {
            state.player?.removeListener(listener)
        }
    }

    LaunchedEffect(state.player) {
        while (true) {
            component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.UpdateProgress)
            delay(1000L)
            if (state.currentPosition > state.duration)
                if (state.trackIndex != state.songList.lastIndex)
                    component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.Next)
                else
                    component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.Pause)
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = if (state.song?.isAlbumArtExists == true)
                state.song?.albumArtPath
            else
                R.drawable.music_icon,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .padding(animatedImage)
                .shadow(
                    elevation = if (state.isPaused) 2.dp else 8.dp,
                    shape = RoundedCornerShape(16.dp)
                )
        )
        Spacer(modifier = Modifier.height(12.dp))
        SongPlayerSlider(
            currentPosition = state.currentPosition.toFloat(),
            songName = state.song?.name ?: "Нет названия",
            songArtist = state.song?.artist ?: "Нет исполнителя",
            onValueChangeFinished = { component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.Seek(it.toLong())) },
            duration = state.duration.toFloat(),
            formattedDuration = state.song?.duration ?: "00:00"
        )
        Spacer(modifier = Modifier.height(50.dp))
        SongPlayerButtonsRow(
            onPlayButtonClick = {
                if(state.isPaused)
                    component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.Play)
                else
                    component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.Pause)
            },
            onNextButtonClick = { component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.Next) },
            onBackButtonClick = { component.processIntent(com.example.mvi.main.songPlayer.SongPlayerIntent.Back) },
            isPaused = state.isPaused
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun SongPlayerSlider(
    currentPosition: Float,
    songName: String,
    songArtist: String,
    onValueChangeFinished: (Float) -> Unit,
    duration: Float,
    formattedDuration: String
) {
    var sliderPosition by remember(currentPosition) { mutableFloatStateOf(currentPosition) }

    var formattedSliderPosition by remember { mutableStateOf("00:00") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(
            text = songName,
            maxLines = 2,
            fontSize = 20.sp
        )
        Text(
            text = songArtist,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            valueRange = 0f..duration,
            onValueChangeFinished = { onValueChangeFinished(sliderPosition) },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary
            )
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = formattedSliderPosition,
                fontSize = 16.sp
            )
            Text(
                text = formattedDuration,
                fontSize = 16.sp
            )
        }
        LaunchedEffect(sliderPosition) {
            val minutes = (sliderPosition.toLong() / 1000) / 60
            val seconds = (sliderPosition.toLong() / 1000) % 60
            formattedSliderPosition = String.format("%02d:%02d", minutes, seconds)
        }
    }
}

@Composable
fun SongPlayerButtonsRow(
    onPlayButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    isPaused: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        SongPlayerIconButton(
            onButtonClick = onBackButtonClick,
            icon = ImageVector.vectorResource(R.drawable.back),
            contentDescription = "button back",
            modifier = Modifier.size(40.dp)
        )
        SongPlayerIconButton(
            onButtonClick = onPlayButtonClick,
            icon = if(isPaused)
                ImageVector.vectorResource(R.drawable.play_button)
            else
                ImageVector.vectorResource(R.drawable.pause_button),
            contentDescription = "button play",
            modifier = Modifier.size(80.dp)
        )
        SongPlayerIconButton(
            onButtonClick = onNextButtonClick,
            icon = ImageVector.vectorResource(R.drawable.next),
            contentDescription = "button next",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun SongPlayerIconButton(
    onButtonClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onButtonClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}