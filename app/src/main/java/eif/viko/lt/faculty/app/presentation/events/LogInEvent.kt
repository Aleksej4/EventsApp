package eif.viko.lt.faculty.app.presentation.events

sealed class LogInEvent { //write what kind of actions screen will have, example -  buttons, refresh something or delete
    data class OnLogInClick(
        val username: String,
        val password: String
    ): LogInEvent()
    data object OnRegisterClick: LogInEvent()
}