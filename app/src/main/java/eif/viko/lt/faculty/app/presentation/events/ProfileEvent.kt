package eif.viko.lt.faculty.app.presentation.events

sealed class ProfileEvent {
    data object OnCreateEventClick: ProfileEvent()
    data object OnSavedEventsClick: ProfileEvent()
    data object OnProfileSettingsClick: ProfileEvent()
    data object OnLogOutClick: ProfileEvent()
}