package eif.viko.lt.faculty.app.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.userManaging.User
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.data.userManaging.UserRepository
import eif.viko.lt.faculty.app.presentation.events.ProfileSettingsEvent
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ProfileSettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userManager: UserManager
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")

    init {
        viewModelScope.launch {
            userRepository.getUser(userManager.getUserId().toString())?.let { user ->
                firstName = user.firstName
                lastName = user.lastName
            }
        }
    }

    fun onEvent(event: ProfileSettingsEvent){
        when(event){
            is ProfileSettingsEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ProfileSettingsEvent.OnSaveClick -> {
                viewModelScope.launch {
                    Log.e("INFO", firstName)
                    userRepository.updateUser(User(
                        firstName = firstName,
                        lastName = lastName,
                        id = userManager.getUserId().toString()
                    ))
                }
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}