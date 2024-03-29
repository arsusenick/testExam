package com.example.examapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examapp.model.Id
import com.example.examapp.model.Location
import com.example.examapp.model.Name
import com.example.examapp.model.Picture

@Entity
data class Persona(
    @PrimaryKey(autoGenerate = true)
    val idKey: Int = 0,
    @Embedded
    val name: Name,
    val gender: String,
    val age: Int,
    val birthDate: String,
    @Embedded
    val location: Location,
    @Embedded
    val id: Id? = null,
    @Embedded
    val picture: Picture? = null,
    @Embedded
    val contact: Contact,
    val nat: String
)

data class Contact(
    val workPhone: String,
    val selfPhone: String,
    val email: String
)
