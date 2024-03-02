package com.example.examapp.viewModel


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examapp.database.Contact
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
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class MainViewModel(private val repository: Repository, val database: PersonDatabase) :ViewModel() {

    val myResponse: MutableLiveData<Response<Person>> = MutableLiveData()
    var personCount = mutableIntStateOf(0)
    val newSeed = mutableStateOf("")
    val newName = mutableStateOf(Name("", "", ""))
    val newGender = mutableStateOf("")
    val newAge = mutableIntStateOf(0)
    val newBirthDate = mutableStateOf("")
    val newLocation = mutableStateOf(Location(Street("", ""), "", "", "", "", Coordinates(.0, .0)))
    val newId = mutableStateOf(Id("",""))
    val newPicture = mutableStateOf(Picture("",""))
    val newContact = mutableStateOf(Contact("","",""))
    fun insertItem() = viewModelScope.launch {
        val persona = Persona(
            seed = newSeed.value,
            name = newName.value,
            gender = newGender.value,
            age = newAge.intValue,
            birthDate = newBirthDate.value,
            location = newLocation.value,
            id = newId.value,
            picture = newPicture.value,
            contact = newContact.value
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
        newContact.value = Contact("","","")
        getCountPerson()
    }

    fun deleteAll() = viewModelScope.launch {
        database.dao.deleteAllPersons()
        getCountPerson()
    }

    fun getCountPerson() = viewModelScope.launch{
        personCount.intValue = database.dao.getCountOfElements()
    }

    fun getPerson() {
        viewModelScope.launch {
            val response = repository.getPerson()
            myResponse.value = response
        }
    }

    fun downloadAndSaveImage(context: Context, imageUrl: String, fileName: String) {
        createDir(context)
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {
                    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
                    try {
                        val fos = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos.flush()
                        fos.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: android.graphics.drawable.Drawable?) {
                e?.printStackTrace()
            }

            override fun onPrepareLoad(placeHolderDrawable: android.graphics.drawable.Drawable?) {}
        }

        Picasso.get().load(imageUrl).into(target)
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