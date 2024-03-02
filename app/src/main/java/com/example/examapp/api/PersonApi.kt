package com.example.examapp.api


import com.example.examapp.model.Person
import retrofit2.Response
import retrofit2.http.GET

interface PersonApi {

    @GET("api/")
    suspend fun getPerson(): Response<Person>
}