package com.example.aswcms.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.domain.GoogleSignInManager
import com.example.aswcms.domain.SignInResult
import com.example.aswcms.domain.repositories.AuthenticationRepository
import com.example.aswcms.domain.repositories.AuthenticationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val googleManager: GoogleSignInManager): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _effects = MutableSharedFlow<LoginEffect>()
    val effects: SharedFlow<LoginEffect> = _effects

    fun onIntent(intent: LoginIntent) {
        when(intent) {
            is LoginIntent.LoginToGoogle -> retrieveNonceFromGoogle(intent.context)
        }
    }

    private fun retrieveNonceFromGoogle(context: Context) {
        viewModelScope.launch {
            when (val result = googleManager.signIn(context)) {
                SignInResult.CancelledByUser -> {}
                is SignInResult.Failure -> _effects.emit(LoginEffect.ShowError(result.cause.message?: "Unknown Google Sign In Error"))

                is SignInResult.Success -> signIn(result.token)
            }
        }
    }

    private fun signIn(
        token: String,
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = repository.login(token)) {
                is AuthenticationResult.LoginSuccess -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        signedIn = true
                    )
                    _effects.emit(LoginEffect.ShowSignInSuccess)
                }

                is AuthenticationResult.LoginFailure -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                    _effects.emit(
                        LoginEffect.ShowError("Sign in failed")
                    )
                }
            }
        }
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val signedIn: Boolean = false,
    val error: String? = null
)

sealed interface LoginEffect {
    data object ShowSignInSuccess : LoginEffect
    data class ShowError(val message: String) : LoginEffect
}

sealed interface LoginIntent{
    data class LoginToGoogle(val context: Context): LoginIntent
}