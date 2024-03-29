package com.example.examapp.repository

import com.example.examapp.api.RetrofitInstance
import com.example.examapp.model.Person
import retrofit2.Response

class Repository {

    suspend fun getPerson(id: Int): Response<Person> {
        return RetrofitInstance.api.getPerson(id)
    }
}