package com.example.examapp.api


import com.example.examapp.model.Person
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonApi {

    @GET("api/")
    suspend fun getPerson(@Query("results") id: Int): Response<Person>
}