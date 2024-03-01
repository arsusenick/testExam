package com.example.examapp.viewModel


import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examapp.database.PersonDatabase
import com.example.examapp.database.Persona
import com.example.examapp.model.Coordinates
import com.example.examapp.model.Id
import com.example.examapp.model.Location
import com.example.examapp.model.Name
import com.example.examapp.model.Person
import com.example.examapp.model.Picture
import com.example.examapp.model.Street
import com.example.examapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository, private val database: PersonDatabase) :ViewModel() {

    val myResponse: MutableLiveData<Response<Person>> = MutableLiveData()

    val newSeed = mutableStateOf("")
    val newName = mutableStateOf(Name("", "", ""))
    val newGender = mutableStateOf("")
    val newAge = mutableIntStateOf(0)
    val newBirthDate = mutableStateOf("")
    val newLocation = mutableStateOf(Location(Street("", ""), "", "", "", "", Coordinates(.0, .0)))
    val newId = mutableStateOf(Id("",""))
    val newPicture = mutableStateOf(Picture("",""))

    fun insertItem() = viewModelScope.launch {
        val persona = Persona(
            seed = newSeed.value,
            name = newName.value,
            gender = newGender.value,
            age = newAge.intValue,
            birthDate = newBirthDate.value,
            location = newLocation.value,
            id = newId.value,
            picture = newPicture.value
        )
        database.dao.insertPerson(persona)
        newSeed.value = ""
        newName.value = Name("", "", "")
        newGender.value = ""
        newAge.intValue = 0
        newBirthDate.value = ""
        newLocation.value = Location(Street("", ""), "", "", "", "", Coordinates(.0, .0))
        newId.value = Id("","")
        newPicture.value = Picture("","")
    }

    fun deleteAll() = viewModelScope.launch {
        database.dao.deleteAllPersons()
    }

    fun getPerson() {
        viewModelScope.launch {
            val response = repository.getPerson()
            myResponse.value = response
        }
    }
}