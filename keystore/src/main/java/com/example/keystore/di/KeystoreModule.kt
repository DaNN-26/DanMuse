package com.example.keystore.di

import android.content.Context
import com.example.keystore.KeystoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeystoreModule {
    @Singleton
    @Provides
    fun provideKeystoreManager(
        @ApplicationContext context: Context
    ) = KeystoreManager(context)
}