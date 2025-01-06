package com.example.danmuse.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.danmuse.MainActivity
import com.example.media.controller.domain.SongController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SongService : MediaSessionService() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    @Inject
    lateinit var controller: SongController

    private lateinit var mediaSession: MediaSession

    override fun onCreate() {
        super.onCreate()

        val sessionIntent = Intent(this, MainActivity::class.java)
        val sessionPendingIntent = PendingIntent.getActivity(
            this,
            0,
            sessionIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        mediaSession = MediaSession.Builder(this, exoPlayer)
            .setSessionActivity(sessionPendingIntent)
            .build()

        startForegroundWithNotification()
        //observeSongState()
    }

    @OptIn(UnstableApi::class)
    private fun startForegroundWithNotification() {
        val channelId = "MediaPlaybackChannel"
        val channelName = "DanMuse"

        val notificationManager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("DanMuse")
                .setContentText(controller.songState.value.song?.name ?: "Ничего не играет")
                .setSmallIcon(android.R.drawable.ic_media_play)
//                .setStyle(
//                    NotificationCompat.MediaStyle()
//                        .setMediaSession(mediaSession.sessionCompatToken))

                .build()


        // Start foreground service with notification
        startForeground(1, notification)
    }

    private fun updateNotification(songTitle: String) {
        val channelId = "MediaPlaybackChannel"

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("DanMuse")
            .setContentText(songTitle)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(1, notification)
    }

    //TODO Обсервить по новому, т.к. value переделал во flow
//    private fun observeSongState() {
//         controller.songState.subscribe { songState ->
//            updateNotification(songState.song?.name ?: "Ничего не играет")
//        }
//    }
    //TODO Обсервить по новому, т.к. value переделал во flow

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        mediaSession.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession
}
