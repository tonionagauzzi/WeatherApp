package com.vitantonio.nagauzzi.weatherapp.repository.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * APIクライアントを提供するネットワークモジュール
 */
object NetworkModule {
    private const val GEOCODING_API_BASE_URL = "https://geocoding.geo.census.gov/geocoder/"
    private const val OPEN_METEO_API_BASE_URL = "https://api.open-meteo.com/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    /**
     * Geocoding APIクライアントを提供する
     */
    val geocodingService: GeocodingService by lazy {
        Retrofit.Builder()
            .baseUrl(GEOCODING_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeocodingService::class.java)
    }

    /**
     * Open Meteo APIクライアントを提供する
     */
    val openMeteoService: OpenMeteoService by lazy {
        Retrofit.Builder()
            .baseUrl(OPEN_METEO_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OpenMeteoService::class.java)
    }
}
