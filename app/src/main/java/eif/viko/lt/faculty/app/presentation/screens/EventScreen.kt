package eif.viko.lt.faculty.app.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import eif.viko.lt.faculty.app.presentation.components.FilledButtonComponent
import eif.viko.lt.faculty.app.presentation.events.EventEvent
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import eif.viko.lt.faculty.app.presentation.viewModels.EventViewModel

@Composable
fun EventScreen (
    viewModel: EventViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
){
    var status by rememberSaveable {
        mutableStateOf("")
    }
    var icon by remember {
        mutableStateOf(Icons.Outlined.Place)
    }
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    if (viewModel.savedEvent) {
        status = "Participating"
        icon = Icons.Filled.LocationOn
    } else {
        status = "Not participating"
        icon = Icons.Outlined.Place
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Primary,
    ) {
        Box {
            if (viewModel.eventCreator()){
                Box (
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete event",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                viewModel.onEvent(EventEvent.OnDeleteClick)
                            }
                    )
                }
            }
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = viewModel.title,
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(0.dp, 24.dp, 0.dp, 24.dp)
                )
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ){
                    Image(
                        painter = rememberAsyncImagePainter(model = viewModel.image),
                        contentDescription = viewModel.title + " Image",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.5f)
                                .width(50.dp)
                                .padding(16.dp, 0.dp, 0.dp, 0.dp),
                            contentAlignment = Alignment.CenterStart
                        ){
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "AddToFavorite",
                                    tint = DarkerGreen,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            viewModel.onEvent(EventEvent.OnSaveClick)
                                        }
                                )
                                Text(
                                    text = "Status: $status",
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(8.dp, 0.dp,0.dp,0.dp)
                                )
                            }
                        }
                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.5f)
                                .width(50.dp)
                                .padding(0.dp, 0.dp, 16.dp, 0.dp),
                            contentAlignment = Alignment.CenterEnd
                        ){
                            Text(
                                text = "Date: " + viewModel.date,
                                fontFamily = FontFamily.Serif,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Content",
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
                Box (
                    modifier = Modifier
                        .fillMaxWidth()

                    ){
                    LazyColumn (
                        modifier = Modifier
                            .padding(12.dp)
                    ){
                        item{
                            Text(
                                text = "Category: " + viewModel.category,
                                fontFamily = FontFamily.Serif,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 0.dp, 6.dp)
                            )
                            Text(
                                text = "Description: " + viewModel.description,
                                fontFamily = FontFamily.Serif,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(0.dp, 6.dp, 0.dp, 0.dp)
                            )
                        }
                    }
                }

            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(16.dp)
        ){
            FilledButtonComponent(
                onClick = { viewModel.onEvent(EventEvent.OnBackClick) },
                buttonText = "Back"
            )
        }
    }
}