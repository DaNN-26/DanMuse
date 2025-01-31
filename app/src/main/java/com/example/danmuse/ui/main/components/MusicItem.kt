package com.example.danmuse.ui.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.danmuse.R
import com.example.media.model.Song

@Composable
fun MusicItem(
    song: Song,
    formattedCurrentPosition: String,
    currentSong: Song?,
    onMusicItemClick: (Song) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMusicItemClick(song) }
    ) {
        if(currentSong == song)
            Divider(modifier = Modifier.size(width = 3.dp, height = 65.dp))
        AsyncImage(
            model = if(song.isAlbumArtExists)
                ImageRequest.Builder(LocalContext.current)
                    .data(song.albumArtPath)
                    .crossfade(true)
                    .build()
            else
                R.drawable.music_icon,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(65.dp)
                .padding(6.dp)
                .shadow(2.dp, RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(220.dp)
        ) {
            Text(
                text = song.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = song.artist ?: "Исполнитель неизвестен",
                color = MaterialTheme.colorScheme.tertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = if(currentSong == song)
                formattedCurrentPosition
            else
                song.duration,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}