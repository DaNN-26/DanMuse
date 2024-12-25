package com.example.danmuse.util

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun Long.formatDuration(): String {
    val minutes = (this / 1000) / 60
    val seconds = (this / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}