package eif.viko.lt.faculty.app.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.data.userManaging.UserRepository
import eif.viko.lt.faculty.app.presentation.events.RegisterEvent
import eif.viko.lt.faculty.app.presentation.util.ScreenRoutes
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: EventRepository,
    private val userRepository: UserRepository,
    private val userManager: UserManager
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.OnRegisterClick -> {
                auth.createUserWithEmailAndPassword(event.username, event.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.uid?.let { userId ->
                                userManager.setUserId(userId)
                            }
                            viewModelScope.launch {
                                repository.insertUser(userManager.getUserId().toString(), event.firstName, event.lastName)
                                userRepository.insertUser(userManager.getUserId().toString())
                            }
                            sendUiEvent(UiEvent.Navigate(ScreenRoutes.BOTTOM_BAR_NAVIGATION))
                        } else {
                            Log.w("Registration:", "User registration failed", task.exception)
                        }
                    }
            }
            is RegisterEvent.OnBackClick -> {
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.LOG_IN))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}