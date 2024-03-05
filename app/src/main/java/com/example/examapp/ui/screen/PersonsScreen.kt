package com.example.examapp.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.examapp.util.Constants.Companion.PADDING_SPACING
import com.example.examapp.viewModel.MainViewModel

@Composable
fun PersonsScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val allObjects by viewModel.database.dao.getPerson().collectAsState(emptyList())
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = PADDING_SPACING),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        content = {
            when (allObjects.size) {
                0 -> {
                    item("empty") {
                        Log.d("wertyuiop", "blue")
                        Text(text = "Что то пошло не так", fontSize = 40.sp)
                    }
                }

                else -> {
                    items(allObjects.size) {
                        Log.d("wertyuiop", "churka $it")
                        PersonCard(
                            allObjects[it],
                            navController
                        )
                    }
                }
            }
        })
}

@Composable
fun BlueScreen() {

}