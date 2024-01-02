package eif.viko.lt.faculty.app.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.categories.Categories
import eif.viko.lt.faculty.app.data.userManaging.UserRepository
import eif.viko.lt.faculty.app.presentation.events.HomeEvent
import eif.viko.lt.faculty.app.presentation.util.BottomAppBarScreenRoutes
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: EventRepository,
    private val categories: Categories,
): ViewModel() {

    init {
        categories.setCategories()
    }

    val events = repository.getEvents()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.OnEventClick -> {
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