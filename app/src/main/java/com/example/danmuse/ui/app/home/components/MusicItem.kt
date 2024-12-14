package com.example.danmuse.ui.app.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.danmuse.R
import com.example.danmuse.model.Song

@Composable
fun MusicItem(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
    ) {
        if (!song.isAlbumArtExists) {
            Image(
                painter = painterResource(R.drawable.music_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(65.dp).padding(6.dp).shadow(1.dp, RoundedCornerShape(8.dp))
            )
        } else {
            AsyncImage(
                model = song.albumArtPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(65.dp).padding(6.dp).clip(RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 32.dp)
        ) {
            Text(
                text = song.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = song.duration,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}