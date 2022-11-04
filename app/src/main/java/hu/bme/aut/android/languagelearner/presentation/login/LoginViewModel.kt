package hu.bme.aut.android.languagelearner.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.languagelearner.domain.use_case.ValidateEmail
import hu.bme.aut.android.languagelearner.domain.use_case.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
   // private val network: NetworkInterface,

    ) : ViewModel() {

    var state by mutableStateOf(LoginState())

    private val validationEventChannel = Channel<LoginScreenEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            /*if (MainViewModel.state.stayLogin) {
                val token =
                    network.login(MainViewModel.state.email!!, MainViewModel.state.password!!)
                val jwt = JWT(token)
                val id = jwt.getClaim("id").asInt()
                val name = jwt.getClaim("name").asString()
                val email = jwt.getClaim("email").asString()
                val privilege = when (jwt.getClaim("priv").asString()) {
                    "Admin" -> User.Privilege.Admin
                    "User" -> User.Privilege.User
                    "Handler" -> User.Privilege.Handler
                    else -> User.Privilege.User
                }
                MainViewModel.state = MainViewModel.state.copy(
                    id = id,
                    name = name,
                    email = email,
                    privilege = privilege,
                    token = token,
                )
                validationEventChannel.send(LoginScreenEvent.LoginSuccess)
            }*/
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                val emailResult = validateEmail.execute(event.email)
                state = state.copy(email = event.email, emailError = emailResult.errorMessage)
            }
            is LoginEvent.PasswordChanged -> {
                val passwordResult = validatePassword.execute(event.password)
                state = state.copy(
                    password = event.password,
                    passwordError = passwordResult.errorMessage
                )
            }
            is LoginEvent.StayLoggedInCheckBoxChanged -> {
                state = state.copy(stayLoggedIn = event.stayLoggedIn)
            }
            is LoginEvent.Login -> {
                state = state.copy(isLoading = true)
                submitData()
            }
            is LoginEvent.CancelLogout -> {
                cancelLogout()
            }
            is LoginEvent.Logout -> {
                logout()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            /*try {
                val token = network.login(state.email, state.password)
                val jwt = JWT(token)
                val id = jwt.getClaim("id").asInt()
                val name = jwt.getClaim("name").asString()
                val email = jwt.getClaim("email").asString()
                val privilege = when(jwt.getClaim("priv").asString()){
                    "Admin" -> User.Privilege.Admin
                    "User" -> User.Privilege.User
                    "Handler" -> User.Privilege.Handler
                    else -> User.Privilege.User
                }
                MainViewModel.state = MainViewModel.state.copy(id = id, name = name, email = email, privilege = privilege, token = token, password = state.password)
                if (state.stayLoggedIn) {
                    appSettingsRepository.set(MainViewModel.state.copy(stayLogin = true))
                } else {
                    appSettingsRepository.set(AppSettings())
                }
                validationEventChannel.send(LoginScreenEvent.LoginSuccess)
            } catch (e: Exception) {
                validationEventChannel.send(LoginScreenEvent.LoginFailed)
            }*/
        }
    }

    private fun cancelLogout() {
        viewModelScope.launch {
            validationEventChannel.send(LoginScreenEvent.LogoutFailed)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            /*state = state.copy(isLoading = true)
            MainViewModel.state = MainViewModel.state.copy(id = null, email = null, name = null, privilege = null, stayLogin = false, password = null, token = null)
            appSettingsRepository.set(AppSettings())
            validationEventChannel.send(LoginScreenEvent.LogoutSuccess)
            state = state.copy(isLoading = false)*/
        }
    }

    sealed class LoginScreenEvent {
        object LoginSuccess : LoginScreenEvent()
        object LoginFailed : LoginScreenEvent()
        object LogoutSuccess : LoginScreenEvent()
        object LogoutFailed : LoginScreenEvent()
    }
}