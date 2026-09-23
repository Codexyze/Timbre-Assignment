package com.nutrino.timbreassignment.di.modules

import android.content.Context
import com.nutrino.timbreassignment.core.media.MediaPlayerManager
import com.nutrino.timbreassignment.data.repoimpl.MediaEditingRepoImpl
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import com.nutrino.timbreassignment.domain.usecase.GetAllSongsUseCase
import com.nutrino.timbreassignment.domain.usecase.GetAllVideosUseCase
import com.nutrino.timbreassignment.domain.usecase.TrimAudioUseCase
import com.nutrino.timbreassignment.domain.usecase.TrimVideoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Main Hilt dependency injection module installed in [SingletonComponent].
 * Provides application-level singletons including [MediaPlayerManager], [MediaEditingRepository],
 * and domain use cases.
 */
@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    /**
     * Provides a singleton instance of [MediaPlayerManager].
     *
     * @param context Application context injected via Hilt.
     * @return [MediaPlayerManager] instance.
     */
    @Provides
    @Singleton
    fun provideMediaPlayerManager(@ApplicationContext context: Context): MediaPlayerManager {
        return MediaPlayerManager(context = context)
    }

    /**
     * Provides [MediaEditingRepository] implementation.
     *
     * @param repositoryImpl Concrete repository implementation [MediaEditingRepoImpl].
     * @return [MediaEditingRepository] interface instance.
     */
    @Provides
    @Singleton
    fun provideMediaEditingRepository(
        repositoryImpl: MediaEditingRepoImpl
    ): MediaEditingRepository {
        return repositoryImpl
    }

    /**
     * Provides [GetAllSongsUseCase] instance.
     *
     * @param repository Injected [MediaEditingRepository].
     * @return [GetAllSongsUseCase] instance.
     */
    @Provides
    fun provideGetAllSongsUseCase(
        repository: MediaEditingRepository
    ): GetAllSongsUseCase {
        return GetAllSongsUseCase(repository = repository)
    }

    /**
     * Provides [GetAllVideosUseCase] instance.
     *
     * @param repository Injected [MediaEditingRepository].
     * @return [GetAllVideosUseCase] instance.
     */
    @Provides
    fun provideGetAllVideosUseCase(
        repository: MediaEditingRepository
    ): GetAllVideosUseCase {
        return GetAllVideosUseCase(repository = repository)
    }

    /**
     * Provides [TrimAudioUseCase] instance.
     *
     * @param repository Injected [MediaEditingRepository].
     * @return [TrimAudioUseCase] instance.
     */
    @Provides
    fun provideTrimAudioUseCase(
        repository: MediaEditingRepository
    ): TrimAudioUseCase {
        return TrimAudioUseCase(repository = repository)
    }

    /**
     * Provides [TrimVideoUseCase] instance.
     *
     * @param repository Injected [MediaEditingRepository].
     * @return [TrimVideoUseCase] instance.
     */
    @Provides
    fun provideTrimVideoUseCase(
        repository: MediaEditingRepository
    ): TrimVideoUseCase {
        return TrimVideoUseCase(repository = repository)
    }
}
