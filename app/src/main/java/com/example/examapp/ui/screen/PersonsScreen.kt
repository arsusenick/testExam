package com.example.examapp.ui.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.examapp.MainActivity
import com.example.examapp.util.Constants.Companion.PADDING_SPACING
import com.example.examapp.viewModel.MainViewModel

@Composable
fun PersonsScreen(
    viewModel: MainViewModel,
    context: Context
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
                        Text(text = "СДЕСЯ НИХУЯ НЕТ", fontSize = 40.sp)
                    }
                }

                else -> {
                    allObjects.size.let {
                        items(it) {
                            Log.d("wertyuiop", "churka $it")
                            PersonCard(
                                "${allObjects[it].name.title} ${allObjects[it].name.first_name} ${allObjects[it].name.last_name}",
                                allObjects[it].contact.selfPhone,
                                "${allObjects[it].location.country}, ${allObjects[it].location.state}, ${allObjects[it].location.city}, ${allObjects[it].location.street.name1} ${allObjects[it].location.street.number}  ",
                                allObjects[it].picture!!.largePictureURL,
                                context
                            )
                        }
                    }
                }
            }
        })
}

@Composable
fun BlueScreen() {

}