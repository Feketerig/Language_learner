package hu.bme.aut.android.languagelearner.presentation.login

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.bme.aut.android.languagelearner.MainActivity
import hu.bme.aut.android.languagelearner.data.network.WordApi
import hu.bme.aut.android.languagelearner.data.network.dto.RefreshTokenRequestDTO
import hu.bme.aut.android.languagelearner.di.NetworkModule
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import io.ktor.client.plugins.auth.providers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val wordApi: WordApi,
    private val wordRepository: WordRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())

    private var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val validationEventChannel = Channel<LoginScreenEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (sharedPreferences.contains("email") && sharedPreferences.contains("password") && !MainActivity.loggedIn){
                state = state.copy(isLoading = true)
                val loginResponse = wordApi.login(sharedPreferences.getString("email","")!!, sharedPreferences.getString("password","")!!)
                NetworkModule.bearerTokenStorage.add(BearerTokens(loginResponse.accessToken, loginResponse.refreshToken))
                validationEventChannel.send(LoginScreenEvent.LoginSuccess)
                MainActivity.loggedIn = true
                state = state.copy(isLoading = false)
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                state = state.copy(email = event.email,isError = false)
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password,
                    isError = false
                )
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
            is LoginEvent.Name -> {
                sendName(event.name)
            }
        }
    }

    private fun sendName(name: String) {
        viewModelScope.launch {
            wordApi.setName(name)
            validationEventChannel.send(LoginScreenEvent.LoginSuccess)
        }
    }

    private fun submitData() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val loginResponse = wordApi.login(state.email, state.password)
                NetworkModule.bearerTokenStorage.add(BearerTokens(loginResponse.accessToken, loginResponse.refreshToken))
                with(sharedPreferences.edit()){
                    putString("email", state.email)
                    putString("password", state.password)
                    apply()
                }
                MainActivity.loggedIn = true
                if (loginResponse.userDetails.name == null){
                    state = state.copy(isName = true)
                }else{
                    validationEventChannel.send(LoginScreenEvent.LoginSuccess)
                }
            } catch (e: Exception) {
                state = state.copy(isError = true)
                validationEventChannel.send(LoginScreenEvent.LoginFailed)
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun cancelLogout() {
        viewModelScope.launch {
            validationEventChannel.send(LoginScreenEvent.LogoutFailed)
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)
            with(sharedPreferences.edit()){
                remove("email")
                remove("password")
                apply()
            }
            wordRepository.clearAll()
            wordApi.logout(RefreshTokenRequestDTO(NetworkModule.bearerTokenStorage.last().refreshToken))
            MainActivity.loggedIn = false
            validationEventChannel.send(LoginScreenEvent.LogoutSuccess)
            state = state.copy(isLoading = false)
        }
    }

    sealed class LoginScreenEvent {
        object LoginSuccess : LoginScreenEvent()
        object LoginFailed : LoginScreenEvent()
        object LogoutSuccess : LoginScreenEvent()
        object LogoutFailed : LoginScreenEvent()
    }
}