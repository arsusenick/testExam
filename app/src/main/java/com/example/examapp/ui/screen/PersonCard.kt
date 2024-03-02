package com.example.examapp.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.examapp.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun PersonCard(
    name: String,
    phone: String,
    location: String,
    picturePath: String,
    context: Context
) {

    val path = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), picturePath)
    var imgBitmap: Bitmap? = null
    if (path.exists()) {
        Log.d("xzvbfhshfds","path exist")
        imgBitmap = BitmapFactory.decodeFile(path.absolutePath)
    }else Log.d("xzvbfhshfds", "poka ne sush")

    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight(0.13f)
                .background(Color.Red)
        ) {
            Image(
                // on the below line we are specifying the drawable image for our image.
//                 painter = painterResource(id = courseList[it].languageImg),
                painter = rememberAsyncImagePainter(model = imgBitmap),

                // on the below line we are specifying
                // content description for our image
                contentDescription = "Image",

                // on the below line we are setting the height
                // and width for our image.
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(10.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.13f)
                .background(Color.Cyan)
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround
            ) {
            Text(text = name)
            Text(text = phone)
            Text(text = location)
        }
    }
}

//@Composable
//@Preview(heightDp = 700, showBackground = true)
//fun PrewCard(){
//    PersonCard("mr. Crabs Babs", "(214)-435-2345","United States, Boston, okads st., 23")
//}