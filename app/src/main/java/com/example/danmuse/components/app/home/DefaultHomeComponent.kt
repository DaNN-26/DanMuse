package com.example.danmuse.components.app.home

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.danmuse.media.controller.domain.SongController
import com.example.danmuse.media.model.Song
import com.example.danmuse.mvi.app.home.HomeIntent
import com.example.danmuse.mvi.app.home.HomeState
import com.example.danmuse.util.formatDuration
import javax.inject.Inject

class DefaultHomeComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val controller: SongController
) : HomeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(HOME_COMPONENT, HomeState.serializer()) ?: HomeState()
    )

    override val state = _state

    override val songState = controller.songState

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.InitializeSongs -> getSongsFromDevice(intent.context)
            is HomeIntent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = intent.searchQuery) }
                getTracksBySearchQuery()
            }
            is HomeIntent.ClearSearchQuery -> _state.update { it.copy(searchQuery = "") }
            is HomeIntent.OnSongSelected -> controller.selectMusic(intent.song, state.value.songsList)
        }
    }

    private fun getTracksBySearchQuery() {
        _state.update { state ->
            val normalizedQueryParts = state.searchQuery
                .replace(",", "")
                .replace("  ", " ")
                .trim()
                .lowercase()
                .split(" ")

            state.copy(
                filteredSongsList = state.songsList.filter { track ->
                    val normalizedTrackName = track.name
                        .replace(",", "")
                        .replace("  ", " ")
                        .trim()
                        .lowercase()
                    val normalizedArtistName = track.artist
                        ?.replace(",", "")
                        ?.replace("  ", " ")
                        ?.trim()
                        ?.lowercase()

                    normalizedQueryParts.all { queryPart ->
                        normalizedTrackName.contains(queryPart) || (normalizedArtistName?.contains(queryPart) == true)
                    }
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
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        val cursor = context.contentResolver.query(uri, projection, selection, null, null)
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val name = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                val artist = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val data = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val duration = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                val albumId = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                val albumArtPath = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    albumId
                )

                songsList.add(
                    Song(
                        id = id,
                        name = name,
                        artist = artist,
                        path = data,
                        duration = duration.formatDuration(),
                        albumId = albumId,
                        albumArtPath = albumArtPath,
                        isAlbumArtExists = hasAlbumArtUri(context, albumArtPath)
                    )
                )
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

    companion object {
        const val HOME_COMPONENT = "HOME_COMPONENT"
    }
}