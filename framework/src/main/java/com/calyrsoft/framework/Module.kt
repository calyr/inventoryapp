//package com.calyrsoft.framework
//
//import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import okhttp3.MediaType.Companion.toMediaType
//
//single {
//    Retrofit.Builder()
//        .addConverterFactory(
//            Json.asConverterFactory(
//                contentType = "applicationjson".toMediaType()
//            )
//        )
//        .baseUrl()
//        .build()
//}