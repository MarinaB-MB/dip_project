package com.deadely.it_lingua.di

import android.content.Context
import com.deadely.it_lingua.network.ApiService
import com.deadely.it_lingua.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetModule {
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit.Builder =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(client)

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).addInterceptor(ChuckInterceptor(context))
            .callTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService =
        retrofit.build().create(ApiService::class.java)
}
