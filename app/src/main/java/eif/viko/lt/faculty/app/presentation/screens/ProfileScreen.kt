package eif.viko.lt.faculty.app.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import eif.viko.lt.faculty.app.presentation.components.OptionComponent
import eif.viko.lt.faculty.app.presentation.events.ProfileEvent
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.ui.theme.Secondary
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import eif.viko.lt.faculty.app.presentation.viewModels.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
){
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    var isFocused by remember {
        mutableStateOf(false)
    }

    if (isFocused)
        viewModel.refresh()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .onFocusChanged {
                isFocused = it.isFocused
            },
        color = Primary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
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
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = viewModel.firstName + " " + viewModel.lastName,
                        fontSize = 26.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ){
                item {
                    OptionComponent(
                        icon = Icons.Default.Add,
                        title = "Create event",
                        onClick = {viewModel.onEvent(ProfileEvent.OnCreateEventClick)}
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    OptionComponent(
                        icon = Icons.Outlined.Place,
                        title = "Saved events",
                        onClick = {viewModel.onEvent(ProfileEvent.OnSavedEventsClick)}
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    OptionComponent(
                        icon = Icons.Outlined.AccountCircle,
                        title = "Profile settings",
                        onClick = {viewModel.onEvent(ProfileEvent.OnProfileSettingsClick)}
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    OptionComponent(
                        icon = Icons.Outlined.ExitToApp,
                        title = "Log out",
                        onClick = {viewModel.onEvent(ProfileEvent.OnLogOutClick)}
                    )
                }
            }
        }
    }
}