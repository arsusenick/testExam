package com.example.examapp.api

import com.example.examapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val interceptor by lazy {
        HttpLoggingInterceptor()
    }

    private val client by lazy{
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private val retrofit by lazy {
         Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .client(client)
             .build()
    }

    val api: PersonApi by lazy {
        retrofit.create(PersonApi::class.java)
    }
}