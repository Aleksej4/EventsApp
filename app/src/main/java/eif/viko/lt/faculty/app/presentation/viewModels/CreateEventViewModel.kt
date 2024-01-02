package eif.viko.lt.faculty.app.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.Event
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.presentation.events.CreateEventEvent
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val repository: EventRepository,
    private val userManager: UserManager
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateEventEvent){
        when(event){
            is CreateEventEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CreateEventEvent.OnCreateClick -> {
                viewModelScope.launch {
                    repository.insertEvent(
                        event = Event(
                            title = event.title,
                            description = event.description,
                            date = event.date,
                            category = event.category,
                            userId = userManager.getUserId().toString()
                        ),
                        imageUri = event.image
                    )
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