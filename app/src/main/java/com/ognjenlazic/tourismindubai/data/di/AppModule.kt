package com.ognjenlazic.tourismindubai.data.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ognjenlazic.tourismindubai.data.api.Api
import com.ognjenlazic.tourismindubai.data.database.AppDatabase
import com.ognjenlazic.tourismindubai.data.database.PlaceDao
import com.ognjenlazic.tourismindubai.data.database.SoundDao
import com.ognjenlazic.tourismindubai.data.database.VisualDao
import com.ognjenlazic.tourismindubai.utilities.Constants.Companion.BASE_URL
import com.ognjenlazic.tourismindubai.utilities.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    fun provideSoundDao(db: AppDatabase): SoundDao = db.soundDao()

    @Provides
    fun provideVisualDao(db: AppDatabase): VisualDao = db.visualDao()

    @Provides
    fun providePlaceDao(db: AppDatabase): PlaceDao = db.placeDao()
}