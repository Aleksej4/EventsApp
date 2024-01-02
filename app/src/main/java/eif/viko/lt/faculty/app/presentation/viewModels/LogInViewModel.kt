package eif.viko.lt.faculty.app.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.data.userManaging.UserRepository
import eif.viko.lt.faculty.app.presentation.events.LogInEvent
import eif.viko.lt.faculty.app.presentation.util.ScreenRoutes
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userManager: UserManager,
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LogInEvent){
        when(event){
            is LogInEvent.OnLogInClick -> {
                auth.signInWithEmailAndPassword(event.username, event.password)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            sendUiEvent(UiEvent.Navigate(ScreenRoutes.BOTTOM_BAR_NAVIGATION))
                            val user = auth.currentUser
                            user?.uid?.let { userId ->
                                userManager.setUserId(userId)

                                viewModelScope.launch {
                                    userRepository.insertUser(userManager.getUserId().toString())
                                }
                            }
                        } else {
                            //val errorMessage = task.exception?.message ?: "Log In failed"
                        }
                    }

                //sendUiEvent(UiEvent.ShowSnackbar(message = "error"))
            }
            is LogInEvent.OnRegisterClick -> {
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.REGISTER))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
