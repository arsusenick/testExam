package com.example.examapp.viewModel

import android.app.Application
import com.example.examapp.database.PersonDatabase

class App: Application() {
    val database by lazy { PersonDatabase.createDatabase(this) }
}