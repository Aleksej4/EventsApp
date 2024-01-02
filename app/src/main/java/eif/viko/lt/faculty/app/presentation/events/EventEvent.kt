package eif.viko.lt.faculty.app.presentation.events

sealed class EventEvent {
    data object OnBackClick: EventEvent()
    data object OnSaveClick: EventEvent()
    data object OnDeleteClick: EventEvent()
}