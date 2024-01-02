package eif.viko.lt.faculty.app.presentation.events

import eif.viko.lt.faculty.app.data.Event

sealed class HomeEvent {
    data class OnEventClick(
        val event: Event
    ): HomeEvent()
}