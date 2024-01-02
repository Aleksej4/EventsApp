package eif.viko.lt.faculty.app.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.userManaging.User
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.data.userManaging.UserRepository
import eif.viko.lt.faculty.app.presentation.events.ProfileEvent
import eif.viko.lt.faculty.app.presentation.util.BottomAppBarScreenRoutes
import eif.viko.lt.faculty.app.presentation.util.ScreenRoutes
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userManager: UserManager,
    private val userRepository: UserRepository,
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var userData: User? = null
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")

    init {
        viewModelScope.launch {
            userRepository.getUser(userManager.getUserId().toString())?.let { user ->
                firstName = user.firstName
                lastName = user.lastName
                userData = user
            }
        }
    }

    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.OnCreateEventClick -> {
                sendUiEvent(UiEvent.Navigate(BottomAppBarScreenRoutes.CREATE_EVENT))
            }
            is ProfileEvent.OnSavedEventsClick -> {
                sendUiEvent(UiEvent.Navigate(BottomAppBarScreenRoutes.SAVED_EVENTS))
            }
            is ProfileEvent.OnProfileSettingsClick -> {
                sendUiEvent(UiEvent.Navigate(BottomAppBarScreenRoutes.PROFILE_SETTINGS))
            }
            is ProfileEvent.OnLogOutClick -> {
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.LOG_IN))
            }
        }
    }

    fun refresh(){
        viewModelScope.launch {
            userRepository.getUser(userManager.getUserId().toString())?.let { user ->
                firstName = user.firstName
                lastName = user.lastName
                userData = user
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}