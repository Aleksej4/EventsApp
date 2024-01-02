package eif.viko.lt.faculty.app.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eif.viko.lt.faculty.app.presentation.components.FilledButtonComponent
import eif.viko.lt.faculty.app.presentation.components.OutlinedButtonComponent
import eif.viko.lt.faculty.app.presentation.components.TextInputComponent
import eif.viko.lt.faculty.app.presentation.events.RegisterEvent
import eif.viko.lt.faculty.app.presentation.viewModels.RegisterViewModel
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.util.UiEvent

@Composable
fun RegisterScreen (
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }
    }
    var firstNameText by rememberSaveable {
        mutableStateOf("")
    }
    var lastNameText by rememberSaveable {
        mutableStateOf("")
    }
    var usernameText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }
    var repeatPasswordText by rememberSaveable {
        mutableStateOf("")
    }

    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Primary
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextInputComponent(
                value = firstNameText,
                onValueChange = { firstNameText = it},
                visualTransformation = VisualTransformation.None,
                hintText = "First name"
            )

            TextInputComponent(
                value = lastNameText,
                onValueChange = { lastNameText = it},
                visualTransformation = VisualTransformation.None,
                hintText = "Last name"
            )

            TextInputComponent(
                value = usernameText,
                onValueChange = {usernameText = it},
                visualTransformation = VisualTransformation.None,
                hintText = "Email"
            )

            TextInputComponent(
                value = passwordText,
                onValueChange = {passwordText = it},
                visualTransformation = PasswordVisualTransformation(),
                hintText = "Password"
            )

            TextInputComponent(
                value = repeatPasswordText,
                onValueChange = {repeatPasswordText = it},
                visualTransformation = PasswordVisualTransformation(),
                hintText = "Repeat password"
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButtonComponent(
                onClick = {
                    if (firstNameText.isNotBlank() && lastNameText.isNotBlank() && usernameText.isNotBlank() && passwordText.isNotBlank() && repeatPasswordText.isNotBlank()
                        && (passwordText == repeatPasswordText)){
                        viewModel.onEvent(
                            RegisterEvent.OnRegisterClick(
                            firstName = firstNameText,
                            lastName = lastNameText,
                            username = usernameText,
                            password = passwordText,

                        ))
                    }
                },
                buttonText = "Register"
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilledButtonComponent(
                onClick = { viewModel.onEvent(RegisterEvent.OnBackClick) },
                buttonText = "Back"
            )
        }
    }
}