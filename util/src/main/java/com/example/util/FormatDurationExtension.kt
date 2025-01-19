package com.example.util

@Suppress("DefaultLocale")
fun Long.formatDuration(): String {
    val minutes = (this / 1000) / 60
    val seconds = (this / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Suppress("DefaultLocale")
fun Int.formatDuration(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%02d:%02d", minutes, seconds)

}