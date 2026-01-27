package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.domain.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CMSAppViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {

    private val _cmsAppState = MutableStateFlow<CMSAppState>(CMSAppState.Splash)
    val cmsAppState: StateFlow<CMSAppState> = _cmsAppState

    fun onIntent(intent: CMSAppIntent) {
        when (intent) {
            CMSAppIntent.IsLoggedInRequested -> isUserLoggedIn()
            CMSAppIntent.HomeScreenRequested -> _cmsAppState.value = CMSAppState.Main
        }
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch {
            repository.isLoggedIn.collect { isLoggedIn ->
                _cmsAppState.value = if (isLoggedIn) CMSAppState.Main else CMSAppState.Login
            }
        }
    }
}

sealed interface CMSAppState {
    object Splash : CMSAppState
    object Login : CMSAppState
    object Main : CMSAppState
}


sealed interface CMSAppIntent {
    object IsLoggedInRequested : CMSAppIntent
    object HomeScreenRequested : CMSAppIntent
}