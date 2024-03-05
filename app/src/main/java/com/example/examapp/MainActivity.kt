package com.example.examapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examapp.repository.Repository
import com.example.examapp.ui.screen.PersonScreen
import com.example.examapp.ui.screen.PersonsScreen
import com.example.examapp.ui.theme.ExamAppTheme
import com.example.examapp.viewModel.MainViewModel
import com.example.examapp.viewModel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                viewModel.newPersons.value = response.body()
                viewModel.insertItems(applicationContext)
            } else {
                Log.d("wertyuiop", response.errorBody().toString())
                Log.d("wertyuiop", response.code().toString())
            }

        })
        setContent {
            val navController = rememberNavController()
            ExamAppTheme {
                NavHost(navController = navController, startDestination = "PersonsScreen") {
                    composable("PersonsScreen") {
                            viewModel.getCountPerson()
                            PersonsScreen(viewModel = viewModel, navController = navController, context = applicationContext)
                    }
                    composable("PersonScreen/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")
                        PersonScreen(viewModel = viewModel, id = id!!, context = applicationContext)
                    }
                }
            }
        }
    }
}