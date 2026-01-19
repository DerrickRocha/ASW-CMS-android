import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.CMSDependencies
import com.example.aswcms.domain.repositories.AuthenticationRepository
import com.example.aswcms.domain.repositories.AuthenticationResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: AuthenticationRepository = CMSDependencies.authenticationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _effects = MutableSharedFlow<LoginEffect>()
    val effects: SharedFlow<LoginEffect> = _effects

    fun onLoginIntent(
        token: String,
    ) {
        signIn(token)
    }

    private fun signIn(
        token: String,
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = loginRepository.login(token)) {
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


