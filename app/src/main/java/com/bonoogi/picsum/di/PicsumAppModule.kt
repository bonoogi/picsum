package com.bonoogi.picsum.di

import android.content.Context
import androidx.room.Room
import com.bonoogi.picsum.data.PicsumDatabase
import com.bonoogi.picsum.data.image.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import javax.inject.Singleton

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@InstallIn(SingletonComponent::class)
@Module
object PicsumAppModule {

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
    }

    @Singleton
    @Provides
    fun providePicsumService(retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }

    @Singleton
    @Provides
    fun provideSource(service: ImageService): ImageRemoteSource {
        return ImageRemoteSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PicsumDatabase {
        return Room.databaseBuilder(context, PicsumDatabase::class.java, "picsum.db").build()
    }

    @Provides
    fun provideImageDao(database: PicsumDatabase): ImageDao {
        return database.imageDao()
    }

    @Singleton
    @Provides
    fun provideRepository(remoteSource: ImageRemoteSource, dao: ImageDao): ImageRepository {
        return ImageRepositoryImpl(remoteSource, dao)
    }

}