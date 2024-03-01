package com.example.examapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examapp.repository.Repository
import com.example.examapp.ui.theme.ExamAppTheme
import com.example.examapp.viewModel.MainViewModel
import com.example.examapp.viewModel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.myResponse.observe(this, Observer {response ->
            if(response.isSuccessful){
                viewModel.newSeed.value = response.body()!!.info.seed
                viewModel.newName.value = response.body()!!.results[0].name
                viewModel.newGender.value = response.body()!!.results[0].gender
                viewModel.newAge.intValue = response.body()!!.results[0].birth.age
                viewModel.newBirthDate.value = response.body()!!.results[0].birth.date
                viewModel.newLocation.value = response.body()!!.results[0].location
                viewModel.newId.value = response.body()!!.results[0].documents!!
                viewModel.newPicture.value = response.body()!!.results[0].picture
                viewModel.insertItem()
//                Log.d("wertyuiop", response.body()!!.results[0].gender)
//                Log.d("wertyuiop", response.body()!!.results[0].nat)
//                Log.d("wertyuiop", response.body()!!.results[0].name.first_name)
//                Log.d("wertyuiop", response.body()!!.results[0].name.last_name)
            }else{
                Log.d("wertyuiop", response.errorBody().toString())
                Log.d("wertyuiop", response.code().toString())
            }

        })
        setContent {
            ExamAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Row (modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround){
                        Box(modifier = Modifier.size(width = 200.dp, height = 100.dp)) {
                            Button(onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.getPerson()
                                }
                            }) {
                                Text("touch this")
                            }
                        }

                        Box(modifier = Modifier.size(width = 200.dp, height = 100.dp)) {
                            Button(modifier = Modifier.background(Color.Red), onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.deleteAll()
                                }
                            }) {
                                Text(color = Color.White,text = "netouch this")
                            }
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExamAppTheme {
        Greeting("Android")
    }
}