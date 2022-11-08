package hu.bme.aut.android.languagelearner.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isName: Boolean = false,
)
