package com.bonoogi.picsum.di

import com.bonoogi.picsum.data.image.ImageService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
object NetworkModule {

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
}