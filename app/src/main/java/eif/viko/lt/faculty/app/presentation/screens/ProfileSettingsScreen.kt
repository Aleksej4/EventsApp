package eif.viko.lt.faculty.app.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eif.viko.lt.faculty.app.data.userManaging.User
import eif.viko.lt.faculty.app.presentation.components.FilledButtonComponent
import eif.viko.lt.faculty.app.presentation.components.OutlinedButtonComponent
import eif.viko.lt.faculty.app.presentation.components.TextInputComponent
import eif.viko.lt.faculty.app.presentation.events.ProfileSettingsEvent
import eif.viko.lt.faculty.app.presentation.events.SavedEventsEvent
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import eif.viko.lt.faculty.app.presentation.viewModels.ProfileSettingsViewModel

@Composable
fun ProfileSettingsScreen(
    viewModel: ProfileSettingsViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
){
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Primary
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ){
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = DarkerGreen,
                            modifier = Modifier
                                .size(130.dp)
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                ){
                    item {
                        TextInputComponent(
                            value = viewModel.firstName,
                            onValueChange = { viewModel.firstName = it },
                            hintText = "First name",
                            visualTransformation = VisualTransformation.None
                        )
                        TextInputComponent(
                            value = viewModel.lastName,
                            onValueChange = { viewModel.lastName = it },
                            hintText = "Last name",
                            visualTransformation = VisualTransformation.None
                        )
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(16.dp)
        ){
            Row {
                OutlinedButtonComponent(
                    onClick = { viewModel.onEvent(ProfileSettingsEvent.OnSaveClick)},
                    buttonText = "Save"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                FilledButtonComponent(
                    onClick = { viewModel.onEvent(ProfileSettingsEvent.OnBackClick)},
                    buttonText = "Back"
                )
            }
        }
    }
}