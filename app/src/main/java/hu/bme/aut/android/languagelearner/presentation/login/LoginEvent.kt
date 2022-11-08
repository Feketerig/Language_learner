package hu.bme.aut.android.languagelearner.presentation.login

sealed class LoginEvent{
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class Name(val name: String): LoginEvent()

    object Login: LoginEvent()
    object CancelLogout: LoginEvent()
    object Logout: LoginEvent()
}
