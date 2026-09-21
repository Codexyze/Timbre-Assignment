package com.scrymz.timbreassignment.di.modules

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.scrymz.timbreassignment.core.media.MediaPlayerManager
import com.scrymz.timbreassignment.data.repoimpl.MediaEditingRepoImpl
import com.scrymz.timbreassignment.domain.repository.MediaEditingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideMediaPlayerManager(exoPlayer: ExoPlayer): MediaPlayerManager {
        return MediaPlayerManager(exoPlayer = exoPlayer)
    }

    @Provides
    @Singleton
    fun provideMediaEditingRepository(
        repositoryImpl: MediaEditingRepoImpl
    ): MediaEditingRepository {
        return repositoryImpl
    }
}