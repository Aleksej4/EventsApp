package eif.viko.lt.faculty.app.presentation.events

sealed class RegisterEvent {
    data class OnRegisterClick (
        val firstName: String,
        val lastName: String,
        val username: String,
        val password: String
    ): RegisterEvent()
    data object OnBackClick: RegisterEvent()
}