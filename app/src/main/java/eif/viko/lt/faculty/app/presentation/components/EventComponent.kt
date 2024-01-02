package eif.viko.lt.faculty.app.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import eif.viko.lt.faculty.app.data.Event
import eif.viko.lt.faculty.app.presentation.events.HomeEvent
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Secondary

@Composable
fun EventComponent(
    modifier: Modifier,
    event: Event,
) {
    Box (
        modifier = modifier
    ){
        Column {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = event.title,
                    fontFamily = FontFamily.Serif
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .border(
                        border = BorderStroke(1.dp, DarkerGreen),
                    )
            ){
                Image(
                    painter = rememberAsyncImagePainter(model = event.image),
                    contentDescription = event.title + " Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
            ){
                Row (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Box (
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                            .padding(25.dp, 0.dp, 0.dp, 0.dp),
                        contentAlignment = Alignment.CenterStart
                    ){
                    }
                    Box (
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                            .padding(0.dp, 0.dp, 25.dp, 0.dp),
                        contentAlignment = Alignment.CenterEnd
                    ){
                        Text(
                            text = event.date,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            }
        }
    }
}