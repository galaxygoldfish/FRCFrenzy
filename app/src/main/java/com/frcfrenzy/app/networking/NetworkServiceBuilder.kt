package com.frcfrenzy.app.networking

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceBuilder {

    private const val FRC_EVENTS_BASE_URL = "https://www.reddit.com"

    private val gsonObject = GsonBuilder().setLenient().create()
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    ).build()

    fun getNetworkService(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(FRC_EVENTS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonObject))
            .build()
            .create(NetworkService::class.java)
    }

}