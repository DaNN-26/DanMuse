package com.example.danmuse.mvi.songBar

sealed class SongBarIntent {
    data object Pause : SongBarIntent()
    data object Play : SongBarIntent()
    data object Next : SongBarIntent()
    data object UpdateProgress : SongBarIntent()
    data object SetDuration : SongBarIntent()
    data object Close : SongBarIntent()
}