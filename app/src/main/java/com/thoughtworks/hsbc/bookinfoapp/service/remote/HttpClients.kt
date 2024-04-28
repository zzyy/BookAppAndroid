package com.thoughtworks.hsbc.bookinfoapp.service.remote

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpClients {
    private val bookSystemBaseUrl = NetworkConst.baseUrl

    @get:Provides
    @Singleton
    val httpClient = OkHttpClient.Builder()
        .build()

    @Provides
    @Singleton
    fun provideBookSystemRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(bookSystemBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun provideBookRemoteApi(retrofit: Retrofit): BookRemoteApi {
        return retrofit.create(BookRemoteApi::class.java)
    }
}