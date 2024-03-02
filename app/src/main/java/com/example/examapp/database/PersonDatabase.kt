package com.example.examapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examapp.dao.PersonDao

@Database(
    entities = [Persona::class],
    version = 1
)
abstract class PersonDatabase: RoomDatabase(){
    abstract val dao: PersonDao
    companion object {
        fun createDatabase(context: Context): PersonDatabase{
            return Room.databaseBuilder(
                context,
                PersonDatabase::class.java,
                "CoolName_DBa",
            ).build()
        }
    }
}