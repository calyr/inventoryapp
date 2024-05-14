package com.calyrsoft.framework

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitBuilder {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                Json{
                    ignoreUnknownKeys = true
                }.asConverterFactory(
                    contentType = "application/json".toMediaType()
                )
            )
            .build()
    }
    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
