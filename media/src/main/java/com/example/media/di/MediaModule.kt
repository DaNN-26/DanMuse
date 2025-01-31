package com.example.media.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.media.controller.data.SongControllerImpl
import com.example.media.controller.domain.SongController
import com.example.media.vk.VkStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {
    @Singleton
    @Provides
    fun provideMediaPlayer(@ApplicationContext context: Context) =
        ExoPlayer.Builder(context).build()

    @Singleton
    @Provides
    fun provideSongController(player: ExoPlayer): SongController =
        SongControllerImpl(player)

    @Singleton
    @Provides
    fun provideVkStore() = VkStore
}