import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.CMSDependencies
import com.example.aswcms.repositories.GoogleSignInManager
import com.example.aswcms.repositories.SignInResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val googleSignInManager: GoogleSignInManager = CMSDependencies.googleManager
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _effects = MutableSharedFlow<LoginEffect>()
    val effects: SharedFlow<LoginEffect> = _effects

    fun onLoginIntent(
        context: Context,
    ) {
        signIn(context)
    }

    private fun signIn(
        context: Context,
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = googleSignInManager.signIn(context)) {
                SignInResult.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        signedIn = true
                    )
                    _effects.emit(LoginEffect.ShowSignInSuccess)
                }

                SignInResult.CancelledByUser -> {
                    _state.value = _state.value.copy(isLoading = false)
                }

                is SignInResult.Failure -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.cause.message
                    )
                    _effects.emit(
                        LoginEffect.ShowError("Sign in failed")
                    )
                }
            }
        }
    }
}


sealed interface LoginIntent {
    data object SignInRequested : LoginIntent
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


