package eif.viko.lt.faculty.app.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.presentation.events.SavedEventsEvent
import eif.viko.lt.faculty.app.presentation.util.BottomAppBarScreenRoutes
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedEventsViewModel @Inject constructor(
    private val repository: EventRepository,
    private val userManager: UserManager
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val events = repository.getSavedEvents(userManager.getUserId().toString())

    fun onEvent(event: SavedEventsEvent){
        when(event){
            is SavedEventsEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is SavedEventsEvent.OnEventClick -> {
                sendUiEvent(UiEvent.Navigate(BottomAppBarScreenRoutes.EVENT + "?eventId=${event.event.id}"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}