package com.example.danmuse.ui.main.home

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.danmuse.components.main.home.HomeComponent
import com.example.danmuse.ui.main.components.MusicItem
import com.example.media.model.Song
import com.example.mvi.main.home.HomeIntent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(
    component: HomeComponent,
    modifier: Modifier
) {
    val state by component.state.subscribeAsState()
    val context = LocalContext.current

    val songState by component.songState.collectAsState()

    val permissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            rememberPermissionState(android.Manifest.permission.READ_MEDIA_AUDIO)
        else
            rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    Surface(
        modifier = modifier
    ) {
        if (!permissionState.status.isGranted)
            RequestMediaPermission(permissionState)
        else {
            LaunchedEffect(Unit) {
                component.processIntent(HomeIntent.InitializeSongs(context))
            }
            HomeMusicColumn(
                songsList = state.songsList,
                filteredSongsList = state.filteredSongsList,
                searchQuery = state.searchQuery,
                formattedCurrentPosition = songState.formattedCurrentPosition,
                onMusicItemClick = { component.processIntent(HomeIntent.OnSongSelected(it)) },
                currentSong = songState.song
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestMediaPermission(
    permissionState: PermissionState
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Для продолжения необходимо разрешить доступ к медиафайлам.",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text("Запросить доступ")
        }
    }
}

@Composable
fun HomeMusicColumn(
    songsList: List<Song>,
    filteredSongsList: List<Song>,
    searchQuery: String,
    formattedCurrentPosition: String,
    onMusicItemClick: (Song) -> Unit,
    currentSong: Song?
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            if (searchQuery.isEmpty())
                songsList
            else
                filteredSongsList
        ) { song ->
            MusicItem(
                song = song,
                formattedCurrentPosition = formattedCurrentPosition,
                onMusicItemClick = onMusicItemClick,
                currentSong = currentSong
            )
        }
    }
}