package eif.viko.lt.faculty.app.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eif.viko.lt.faculty.app.presentation.components.FilledButtonComponent
import eif.viko.lt.faculty.app.presentation.components.OutlinedButtonComponent
import eif.viko.lt.faculty.app.presentation.components.TextInputComponent
import eif.viko.lt.faculty.app.presentation.events.LogInEvent
import eif.viko.lt.faculty.app.presentation.viewModels.LogInViewModel
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.util.UiEvent

@Composable
fun LogInScreen (
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LogInViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
) {

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.Navigate -> onNavigate(event) //look deeper into launched effect
                //is UiEvent.ShowSnackbar -> {
                //    snackbarHostState.showSnackbar(event.message)
                //}
                else -> Unit
            }
        }
    }

    var usernameText by rememberSaveable {
        mutableStateOf("")
    }

    var passwordText by rememberSaveable {
        mutableStateOf("")
    }

    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Primary,

    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextInputComponent(
                value = usernameText,
                onValueChange = { usernameText = it },
                hintText = "Email",
                visualTransformation = VisualTransformation.None
            )

            TextInputComponent(
                value = passwordText,
                onValueChange = { passwordText = it },
                hintText = "Password",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButtonComponent(
                onClick = {
                    if (usernameText.isNotBlank() && passwordText.isNotBlank()){
                        viewModel.onEvent(LogInEvent.OnLogInClick(
                            username = usernameText,
                            password = passwordText))
                    }
                 },
                buttonText = "Log In"
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilledButtonComponent(
                onClick = { viewModel.onEvent(LogInEvent.OnRegisterClick) },
                buttonText = "Register"
            )
        }
    }
}