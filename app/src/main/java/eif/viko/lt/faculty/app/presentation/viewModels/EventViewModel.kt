package eif.viko.lt.faculty.app.presentation.viewModels

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.Event
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.presentation.events.EventEvent
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: EventRepository,
    private val userManager: UserManager
): ViewModel() {

    private var eventData: Event? = null
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var category by mutableStateOf("")
    var date by mutableStateOf("")
    var image by mutableStateOf("")
    private var userId = ""

    var savedEvent by mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val eventId = savedStateHandle.get<String>("eventId")
        viewModelScope.launch {
            repository.getEventById(eventId.toString())?.let {event ->
                title = event.title
                description = event.description
                category = event.category
                date = event.date
                image = event.image.toString()
                userId = event.userId
                eventData = event
            }

            savedEvent = repository.getSavedEvent(eventId.toString(), userManager.getUserId().toString())
        }
    }

    fun onEvent(event: EventEvent){
        when(event){
            is EventEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is EventEvent.OnSaveClick -> {
                if (savedEvent) {
                    deleteSavedEventAndCheck(
                        userManager.getUserId().toString(),
                        eventData?.id.toString()
                    ) { isDeleted ->
                        savedEvent = !isDeleted
                    }
                } else {
                    viewModelScope.launch {
                        repository.insertSavedEvent(eventData?.id.toString(), userManager.getUserId().toString())
                        savedEvent = repository.getSavedEvent(eventData?.id.toString(), userManager.getUserId().toString())
                    }
                }
            }
            is EventEvent.OnDeleteClick -> {
                viewModelScope.launch {
                    eventData?.let { repository.deleteEvent(it) }
                }
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    fun eventCreator(): Boolean{
        return userId == userManager.getUserId()
    }

    fun deleteSavedEventAndCheck(
        userId: String,
        eventId: String,
        callback: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            repository.deleteSavedEvent(eventId, userId)
            val isDeleted = repository.getSavedEvent(eventId, userId)
            callback.invoke(isDeleted)
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}