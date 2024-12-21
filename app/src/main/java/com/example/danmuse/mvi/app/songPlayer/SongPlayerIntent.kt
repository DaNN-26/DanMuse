package com.example.danmuse.mvi.app.songPlayer

sealed class SongPlayerIntent {
    data object Play : SongPlayerIntent()
    data object Pause : SongPlayerIntent()
    data object Next : SongPlayerIntent()
    data object Back : SongPlayerIntent()
    data object UpdateProgress : SongPlayerIntent()
    data object SetDuration : SongPlayerIntent()
    class Seek(val position: Long) : SongPlayerIntent()
}