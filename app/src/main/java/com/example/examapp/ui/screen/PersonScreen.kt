package com.example.examapp.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.examapp.database.Persona
import com.example.examapp.model.Location
import com.example.examapp.util.Country
import com.example.examapp.viewModel.MainViewModel
import java.text.SimpleDateFormat

@Composable

fun PersonScreen(
    viewModel: MainViewModel,
    id: String
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Log.d("asedasdfgdsag", "$id")
    var persona by remember {
        mutableStateOf<Persona?>(null)
    }
    LaunchedEffect(Unit) {
        persona = viewModel.database.dao.getPersonById(id.toInt())
    }
//    Log.d("asedasdfgdsag","${persona?.name?.title} ${persona?.name?.first_name} ${persona?.name?.last_name}")
    if (persona != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = persona?.picture?.largePictureURL),
//            painter = rememberAsyncImagePainter(model = null),
                contentDescription = "",
                modifier = Modifier
                    .width((screenWidth * 0.65f).dp)
                    .height((screenWidth * 0.65f).dp)
                    .padding(10.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${persona!!.name.title} ${persona!!.name.first_name} ${persona!!.name.last_name}")
                Text(
                    text = "${getYearsString(persona!!.age)} ${
                        when (persona!!.gender) {
                            "male" -> "♂"
                            else -> "♀"
                        }
                    }"
                )
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth().padding(top = 15.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    InfoRow("Национальность", Country.getFullNameByAbbreviation(persona!!.nat))
                    InfoRow("Дата рождения", getCleanDayOfBirth(persona!!.birthDate))
                    InfoRow("Место проживания", getLocation(persona!!.location))
                    InfoRow("Рабочий телефон", persona!!.contact.workPhone)
                    InfoRow("Личный телефон", persona!!.contact.selfPhone)
                    InfoRow("Почта", persona!!.contact.email)
                    InfoRow("Название документа", when (persona!!.id?.name) {
                        "" -> "отсутствует информация"
                        else -> persona!!.id?.name!!
                    })
                    InfoRow("Номер документа", when (persona!!.id?.value) {
                        null -> "отсутствует информация"
                        else -> persona!!.id?.value!!
                    })
                }

            }
        }
    }

}

@Composable
fun InfoRow(title: String, value: String) {
    Column(Modifier.padding(top = 15.dp)) {
        Text(text = title, fontSize = 10.sp, fontWeight = FontWeight(300))
        Text(text = value)
    }
}


fun getYearsString(years: Int): String {
    return when {
        years % 10 == 1 && years % 100 != 11 -> "$years год"
        years % 10 in 2..4 && years % 100 !in 12..14 -> "$years года"
        else -> "$years лет"
    }
}

fun getCleanDayOfBirth(dirtyDOB: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date = inputFormat.parse(dirtyDOB)
    val outputFormat = SimpleDateFormat("dd.MM.yyyy")
    return (outputFormat.format(date))
}

fun getLocation(location: Location): String {
    return "${location.country}, ${location.state}, ${location.city}, ${location.street.name1} " +
            "${location.street.number}, ${location.postcode}"
}

