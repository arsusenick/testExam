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

    @Insert
    suspend fun insertPersons(persons: List<Persona>)

    @Query("SELECT * FROM persona ORDER BY idKey ASC")
    fun getPerson(): Flow<List<Persona>>

    @Query("SELECT * FROM persona WHERE idKey = :personId")
    suspend fun getPersonById(personId: Int): Persona

    @Query("DELETE FROM persona")
    suspend fun deleteAllPersons()

    @Query("SELECT COUNT(*) FROM persona")
    suspend fun getCountOfElements(): Int
}