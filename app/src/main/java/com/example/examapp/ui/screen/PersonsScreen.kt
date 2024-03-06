package com.example.examapp.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = {
        CoroutineScope(
            Dispatchers.IO
        ).launch {
            viewModel.getPerson((6..12).random(), context)
        }
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            state = listState,
            content = {
                when (allObjects.size) {
                    0 -> {
                        item("empty") {
                            Box(
                                modifier = Modifier
                                    .height((LocalConfiguration.current.screenHeightDp).dp)
                                    .fillMaxWidth()
                            ) {
                                if (!isLoading) {
                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 32.sp,
                                                    fontWeight = FontWeight.ExtraLight
                                                )
                                            ) {
                                                append("Список пуст")
                                            }
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraLight)) {
                                                append("\nдля обновления потяните вниз")
                                            }
                                        },
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                        }
                    }

                    else -> {
                        items(allObjects.size) {
                            PersonCard(
                                allObjects[it],
                            ) {
                                navController.navigate("PersonScreen/${allObjects[it].idKey}") {
                                    popUpTo("PersonsScreen") {
                                        saveState = true
                                    }
                                }
                            }
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