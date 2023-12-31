package com.watcha.data.di

import com.watcha.data.api.SearchApi
import com.watcha.data.repository.track.remote.TrackRemoteDataSource
import com.watcha.data.repository.track.remote.TrackRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApiModule {

    @Provides
    @Singleton
    fun provideSearchApi(
        @Named("Retrofit") retrofit: Retrofit
    ) : SearchApi{
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTrackRemoteDataSource(searchApi: SearchApi): TrackRemoteDataSource{
        return TrackRemoteDataSourceImpl(searchApi)
    }
}