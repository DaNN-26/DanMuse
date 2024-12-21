package com.example.danmuse.media.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
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
}