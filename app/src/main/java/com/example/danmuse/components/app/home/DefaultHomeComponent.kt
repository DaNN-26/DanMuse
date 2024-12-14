package com.example.danmuse.components.app.home

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.danmuse.model.Song
import com.example.danmuse.mvi.home.HomeIntent
import com.example.danmuse.mvi.home.HomeState
import java.lang.String.format
import javax.inject.Inject

class DefaultHomeComponent @Inject constructor(
    private val componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(HOME_COMPONENT, HomeState.serializer()) ?: HomeState()
    )

    override val state = _state

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.InitializeSongs -> getSongsFromDevice(intent.context)
            is HomeIntent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = intent.searchQuery) }
                getTracksBySearchQuery()
            }
            is HomeIntent.ClearSearchQuery -> _state.update { it.copy(searchQuery = "") }
        }
    }

    private fun getTracksBySearchQuery() {
        _state.update { state ->
            state.copy(
                filteredSongsList = state.songsList.filter { track ->
                    track.name.contains(state.searchQuery, true)
                }
            )
        }
    }

    private fun getSongsFromDevice(context: Context) {
        val songsList = mutableListOf<Song>()
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        val cursor = context.contentResolver.query(uri, projection, selection, null, null)
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val name = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                val data = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val duration = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                val albumId = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                val albumArtPath = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    albumId
                )

                songsList.add(Song(
                    id = id,
                    name = name,
                    path = data,
                    duration = formatDuration(duration),
                    albumId = albumId,
                    albumArtPath = albumArtPath,
                    isAlbumArtExists = hasAlbumArtUri(context, albumArtPath)
                ))
            }
        }
        _state.update { it.copy(songsList = songsList) }
    }

    private fun hasAlbumArtUri(context: Context, albumArtPath: Uri) =
        try {
            context.contentResolver.openInputStream(albumArtPath)?.use {
                true
            } ?: false
        } catch (e: Exception) {
            false
        }

    @SuppressLint("DefaultLocale")
    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return format("%02d:%02d", minutes, seconds)
    }

    companion object {
        const val HOME_COMPONENT = "HOME_COMPONENT"
    }
}