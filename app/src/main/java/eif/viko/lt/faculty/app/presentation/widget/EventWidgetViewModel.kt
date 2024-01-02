package eif.viko.lt.faculty.app.presentation.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.Event
import eif.viko.lt.faculty.app.data.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class EventWidgetViewModel(
    private val repository: EventRepository
): ViewModel() {

    val events = repository.getEvents()
}