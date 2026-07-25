package com.ognjenlazic.tourismindubai.data.di

import com.ognjenlazic.tourismindubai.data.repository.DefaultTopicsRepository
import com.ognjenlazic.tourismindubai.data.source.RetrofitTopicsRemoteDataSource
import com.ognjenlazic.tourismindubai.data.source.RoomTopicsLocalDataSource
import com.ognjenlazic.tourismindubai.data.source.TopicsLocalDataSource
import com.ognjenlazic.tourismindubai.data.source.TopicsRemoteDataSource
import com.ognjenlazic.tourismindubai.domain.repository.TopicsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

/** Marks the dispatcher used for disk and network work, so tests can swap it for a test one. */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindTopicsRepository(impl: DefaultTopicsRepository): TopicsRepository

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(impl: RetrofitTopicsRemoteDataSource): TopicsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(impl: RoomTopicsLocalDataSource): TopicsLocalDataSource

    companion object {
        @Provides
        @IoDispatcher
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}
