package com.watcha.data.di

import android.content.Context
import androidx.room.Room
import com.watcha.data.db.TrackDao
import com.watcha.data.db.TrackDatabase
import com.watcha.data.repository.track.local.TrackLocalDataSource
import com.watcha.data.repository.track.local.TrackLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {

    @Provides
    @Singleton
    fun provideTrackDatabase(@ApplicationContext context: Context): TrackDatabase{
        return Room.databaseBuilder(
            context,
            TrackDatabase::class.java,"track_db")
            .fallbackToDestructiveMigration() // 버전 변경 시 기존 데이터 삭제
            .build()
    }

    @Provides
    @Singleton
    fun provideTrackDao(trackDatabase: TrackDatabase): TrackDao{
        return trackDatabase.TrackDao()
    }

    @Provides
    @Singleton
    fun provideTrackLocalDataSource(trackDao: TrackDao): TrackLocalDataSource{
        return TrackLocalDataSourceImpl(trackDao)
    }
}