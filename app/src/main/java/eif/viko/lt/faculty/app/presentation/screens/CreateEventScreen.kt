package eif.viko.lt.faculty.app.presentation.screens

import android.net.Uri
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import eif.viko.lt.faculty.app.data.Event
import eif.viko.lt.faculty.app.presentation.components.CategoryComponent
import eif.viko.lt.faculty.app.presentation.components.FilledButtonComponent
import eif.viko.lt.faculty.app.presentation.components.ImagePickerComponent
import eif.viko.lt.faculty.app.presentation.components.OutlinedButtonComponent
import eif.viko.lt.faculty.app.presentation.components.RoundButtonComponent
import eif.viko.lt.faculty.app.presentation.components.TextInputComponent
import eif.viko.lt.faculty.app.presentation.events.CreateEventEvent
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.ui.theme.Secondary
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import eif.viko.lt.faculty.app.presentation.viewModels.CreateEventViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
){
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    var eventName by rememberSaveable {
        mutableStateOf("")
    }
    var date by rememberSaveable {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("yyyy/MM/dd")
                .format(date)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    var category by rememberSaveable {
        mutableStateOf("None")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var imageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    var description by rememberSaveable{
        mutableStateOf("")
    }


    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Primary
    ){
        Box (
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Fill the data about event",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .padding(0.dp, 24.dp, 0.dp, 24.dp)
                )

                TextInputComponent(
                    value = eventName,
                    onValueChange = { eventName = it },
                    hintText = "Event name",
                    singleLine = false,
                    visualTransformation = VisualTransformation.None
                )
                Row (
                    modifier = Modifier
                        .size(width = 300.dp, height = 70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    RoundButtonComponent(
                        onClick = {
                            dateDialogState.show()
                        },
                        icon = Icons.Default.DateRange
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Box (
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                            .border(
                                border = BorderStroke(1.dp, DarkerGreen),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Date: $formattedDate",
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row (
                    modifier = Modifier
                        .size(width = 300.dp, height = 70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    RoundButtonComponent(
                        onClick = {
                            showDialog = true
                        },
                        icon = Icons.Default.List
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Box (
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                            .border(
                                border = BorderStroke(1.dp, DarkerGreen),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Category: $category",
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row (
                    modifier = Modifier
                        .size(width = 300.dp, height = 70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    ImagePickerComponent(
                        onImagePicked = {imageUri = it}
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    if (imageUri == null){
                        Box (
                            modifier = Modifier
                                .height(50.dp)
                                .width(200.dp)
                                .border(
                                    border = BorderStroke(1.dp, DarkerGreen),
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Select image",
                                fontFamily = FontFamily.Serif
                            )
                        }
                    } else {
                        Box (
                            modifier = Modifier
                                .height(70.dp)
                                .width(100.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUri),
                                contentDescription = " Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        border = BorderStroke(2.dp, Color.Black),
                                    ),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                TextInputComponent(
                    value = description,
                    onValueChange = { description = it },
                    visualTransformation = VisualTransformation.None,
                    hintText = "Description",
                    singleLine = false
                )
            }
            MaterialDialog (
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(
                        text = "Ok"
                    )
                    negativeButton(
                        text = "Cancel"
                    )
                }
            ){
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
                    //allowedDateValidator = {
                    //
                    //}
                ){
                    date = it
                }
            }
            if(showDialog){
                CategoryComponent(
                    onCategorySelected = {category = it},
                    onDialogClose = {showDialog = it},
                    category = category
                )
            }
        }
        Box (
            contentAlignment = Alignment.BottomCenter
        ){
            Row (
                modifier = Modifier
                    .padding(16.dp),
            ){
                FilledButtonComponent(
                    onClick = { viewModel.onEvent(CreateEventEvent.OnBackClick)},
                    buttonText = "Back"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedButtonComponent(
                    onClick = {
                        if (imageUri != null && eventName != "" && category != "None" && description != ""){
                            viewModel.onEvent(CreateEventEvent.OnCreateClick(
                                title = eventName,
                                date = formattedDate,
                                category = category,
                                image = imageUri,
                                description = description,
                            ))
                        }
                     },
                    buttonText = "Create"
                )
            }
        }
    }
}