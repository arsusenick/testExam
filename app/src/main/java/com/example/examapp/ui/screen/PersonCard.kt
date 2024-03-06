package com.example.examapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.examapp.database.Persona

@Composable
fun PersonCard(
    person: Persona,
    navClick: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width((screenWidth * 0.3f).dp)
                .height((screenWidth * 0.3f).dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(person.picture?.largePictureURL),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.13f)
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "${person.name.title} ${person.name.first_name} ${person.name.last_name}")
            Text(text = person.contact.selfPhone)
            Text(text = "${person.location.country}, ${person.location.state}, ${person.location.city}, ${person.location.street.name1} ${person.location.street.number}")
        }
    }
}
