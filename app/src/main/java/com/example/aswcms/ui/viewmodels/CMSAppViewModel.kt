package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.CMSDependencies
import com.example.aswcms.domain.repositories.ASWDataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CMSAppViewModel(private val repository: ASWDataStoreRepository = CMSDependencies.aswDataRepository) :
    ViewModel() {

    private val _cmsAppState = MutableStateFlow<CMSAppState>(CMSAppState.Splash)
    val cmsAppState: StateFlow<CMSAppState> = _cmsAppState

    fun onIntent(intent: CMSAppIntent) {
        when (intent) {
            CMSAppIntent.IsLoggedInRequested -> isUserLoggedIn()
            CMSAppIntent.HomeScreenRequested -> _cmsAppState.value = CMSAppState.Home
        }
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch {
            _cmsAppState.value =
                if (repository.isLoggedIn()) CMSAppState.Home else CMSAppState.Login
        }
    }
}

sealed interface CMSAppState {
    object Splash : CMSAppState
    object Login : CMSAppState
    object Home : CMSAppState
}


sealed interface CMSAppIntent {
    object IsLoggedInRequested : CMSAppIntent
    object HomeScreenRequested: CMSAppIntent
}