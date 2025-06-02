package com.example.myapplicationb1203288

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WeatherService {
    @GET("v1/rest/datastore/O-A0003-001")
    suspend fun getWeather(
        @Query("Authorization") key: String,
        @Query("format") format: String = "JSON",
        @Query("limit") limit: Int = 5
    ): Response<WeatherResponse>

    companion object {
        private const val BASE_URL = "https://opendata.cwa.gov.tw/api/"
        
        fun create(): WeatherService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }
}
