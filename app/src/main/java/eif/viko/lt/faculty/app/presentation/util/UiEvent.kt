package eif.viko.lt.faculty.app.presentation.util

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    data object PopBackStack: UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}