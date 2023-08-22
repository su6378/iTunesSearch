package com.watcha.data.di

import com.watcha.data.repository.track.TrackRepositoryImpl
import com.watcha.domain.repository.TrackRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsSearchRepository(
        trackRepository: TrackRepositoryImpl
    ): TrackRepository
}