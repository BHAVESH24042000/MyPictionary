package com.example.mypictionary.di

import android.content.Context
import com.example.mypictionary.remote.api.MyPictionaryApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {  // functkion used to provide single object for okhttp client
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Singleton
    @Provides
    fun providesGsonInstance() : Gson{
        return Gson()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): MyPictionaryApiService {
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(MyPictionaryApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApplicationContext( @ApplicationContext context: Context ) : Context {
        return context
    }


}