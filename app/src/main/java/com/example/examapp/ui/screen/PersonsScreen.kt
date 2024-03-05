package com.example.examapp.ui.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.examapp.util.Constants.Companion.PADDING_SPACING
import com.example.examapp.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonsScreen(
    viewModel: MainViewModel,
    navController: NavController,
    context: Context
) {
    val allObjects by viewModel.database.dao.getPerson().collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = {
        CoroutineScope(
            Dispatchers.IO
        ).launch {
            viewModel.deleteAll()
            viewModel.deleteAllFilesInDirectory(context)
            viewModel.getPerson((6..12).random())
        }
    })
    Box(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = PADDING_SPACING),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            content = {
                when (allObjects.size) {
                    0 -> {
                        item("empty") {
                            Log.d("wertyuiop", "blue")
//                            Text(text = "Что то пошло не так", fontSize = 40.sp)
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
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = if (isLoading) Color.White else Color.White,
        )
    }

}

@Composable
fun BlueScreen() {

}