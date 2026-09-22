package com.nutrino.timbreassignment.di.modules

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.nutrino.timbreassignment.core.media.MediaPlayerManager
import com.nutrino.timbreassignment.data.repoimpl.MediaEditingRepoImpl
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import com.nutrino.timbreassignment.domain.usecase.GetAllSongsUseCase
import com.nutrino.timbreassignment.domain.usecase.GetAllVideosUseCase
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

    @Provides
    fun provideGetAllSongsUseCase(
        repository: MediaEditingRepository
    ): GetAllSongsUseCase {
        return GetAllSongsUseCase(repository = repository)
    }

    @Provides
    fun provideGetAllVideosUseCase(
        repository: MediaEditingRepository
    ): GetAllVideosUseCase {
        return GetAllVideosUseCase(repository = repository)
    }
}
