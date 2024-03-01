package com.example.examapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.examapp.database.Persona
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert
    suspend fun insertPerson(persona: Persona)

    @Query("SELECT * FROM persona ORDER BY seed ASC")
    fun getPerson(): Flow<List<Persona>>

    @Query("DELETE FROM persona")
    suspend fun deleteAllPersons()
}