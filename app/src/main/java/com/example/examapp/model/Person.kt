package com.example.examapp.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp

data class Person(
    @SerializedName("info")
    val info: Info,
    val results: ArrayList<Results>
)

data class Results(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    @SerializedName("dob")
    val birth: Dob,
    @SerializedName("phone")
    val workPhone: String,
    @SerializedName("cell")
    val selfPhone: String,
    @SerializedName("id")
    val documents: Id? = Id(),
    val picture: Picture,
    val nat: String
)

data class Info (
    @SerializedName("seed")
    var seed: String
)

data class Name (
    val title: String,
    @SerializedName("first")
    val first_name: String,
    @SerializedName("last")
    val last_name: String
)

data class Id (
    val name: String? = null,
    val value: String? = null
)

data class Picture(
    @SerializedName("large")
    val largePictureURL: String,
    @SerializedName("medium")
    val mediumPictureURL: String
)

data class Location(
    @Embedded
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    @Embedded
    val coordinates: Coordinates
)

data class Street(
    val number: String,
    @SerializedName("name")
    val name1: String
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

data class Dob(
    val date: String,
    val age: Int
)