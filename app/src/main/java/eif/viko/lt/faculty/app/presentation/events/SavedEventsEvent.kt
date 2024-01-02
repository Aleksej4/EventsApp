package eif.viko.lt.faculty.app.presentation.events

import eif.viko.lt.faculty.app.data.Event

sealed class SavedEventsEvent {
    data object OnBackClick: SavedEventsEvent()
    data class OnEventClick(
        val event: Event
    ): SavedEventsEvent()
}