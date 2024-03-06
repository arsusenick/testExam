package com.example.examapp.viewModel


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.examapp.database.Contact
import com.example.examapp.database.PersonDatabase
import com.example.examapp.database.Persona
import com.example.examapp.model.Person
import com.example.examapp.model.Picture
import com.example.examapp.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class MainViewModel(private val repository: Repository, val database: PersonDatabase) :ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    val myResponse: MutableLiveData<Response<Person>> = MutableLiveData()
    var personCount = mutableIntStateOf(0)
    val newPersons = mutableStateOf<Person?>(null)

    private var persons : MutableList<Persona> = ArrayList()

    fun insertItems(context: Context) = viewModelScope.launch {
        deleteAll()
        deleteAllFilesInDirectory(context)
        createDir(context)
        newPersons.value!!.results.forEach{ person ->
            val picName = person.picture.largePictureURL.substring(36)
            val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), picName)
            downloadAndSaveImage(context, person.picture.largePictureURL, picName)
            while(true){
                delay(100)
                if(directory.exists()){
                    break
                }
            }
            persons.add(
                Persona(
                    name = person.name,
                    gender = person.gender,
                    age = person.birth.age,
                    birthDate = person.birth.date,
                    location = person.location,
                    id = person.documents,
                    picture = Picture(directory.toString(), directory.toString()),
                    contact = Contact(person.workPhone, person.selfPhone, person.email),
                    nat = person.nat
            ))
        }
        database.dao.insertPersons(persons)
        persons = ArrayList()
        getCountPerson()
        _isLoading.value = false
    }
    fun deleteAll() = viewModelScope.launch {
        database.dao.deleteAllPersons()
        getCountPerson()
    }

    fun getCountPerson() = viewModelScope.launch{
        personCount.intValue = database.dao.getCountOfElements()
    }

    @SuppressLint("ShowToast")
    fun getPerson(id: Int, context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getPerson(id)
                myResponse.value = response
            }catch (e: Exception){
                Toast.makeText(context, "Ошибка соединения", Toast.LENGTH_SHORT).show()
                _isLoading.value = false
            }
        }
    }


    fun downloadAndSaveImage(context: Context, url: String, fileName: String) = viewModelScope.launch{
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = imageLoader.execute(request).drawable
        if (result is Drawable) {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
            val fileOutputStream = FileOutputStream(file)
            result.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
        }
    }


    private fun createDir(context: Context){
        val directory1 = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "women")
        val directory2 = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "men")
        if (!directory1.exists()) {
            if (directory1.mkdir()) {
                Log.d("DirectoryCreation", "Directory \"women\" created")
            } else {
                Log.e("DirectoryCreation", "Failed to create directory \"women\"")
            }
        } else {
            Log.d("DirectoryCreation", "Directory \"women\" already exists")
        }

        if (!directory2.exists()) {
            if (directory2.mkdir()) {
                Log.d("DirectoryCreation", "Directory \"men\" created")
            } else {
                Log.e("DirectoryCreation", "Failed to create directory \"men\"")
            }
        } else {
            Log.d("DirectoryCreation", "Directory \"men\" already exists")
        }
    }

    fun deleteAllFilesInDirectory(context: Context) {
        val directory1 = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "women")
        val directory2 = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "men")
        if (directory1.exists() && directory1.isDirectory && directory2.exists() && directory2.isDirectory ) {
            val files = directory1.listFiles()
            val files2 = directory2.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        if (file.delete()) {
                            Log.d("FileDeletion", "File ${file.name} deleted")
                        } else {
                            Log.e("FileDeletion", "Failed to delete file ${file.name}")
                        }
                    }
                }
            }
            if (files2 != null) {
                for (file in files2) {
                    if (file.isFile) {
                        if (file.delete()) {
                            Log.d("FileDeletion", "File ${file.name} deleted")
                        } else {
                            Log.e("FileDeletion", "Failed to delete file ${file.name}")
                        }
                    }
                }
            }
        } else {
            Log.e("DirectoryCheck", "Directory does not exist or is not a directory")
        }
    }


}