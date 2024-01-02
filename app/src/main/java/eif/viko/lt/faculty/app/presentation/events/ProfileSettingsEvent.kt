package eif.viko.lt.faculty.app.presentation.events

import eif.viko.lt.faculty.app.data.userManaging.User

sealed class ProfileSettingsEvent {
    data object OnSaveClick: ProfileSettingsEvent()
    data object OnBackClick: ProfileSettingsEvent()
}