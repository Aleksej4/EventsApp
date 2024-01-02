package eif.viko.lt.faculty.app.presentation.events

import android.net.Uri
import eif.viko.lt.faculty.app.data.Event

sealed class CreateEventEvent {
    data object OnBackClick: CreateEventEvent()
    data class OnCreateClick (
        val category: String,
        val date: String,
        val description: String,
        val image: Uri?,
        val title: String
    ): CreateEventEvent()
}