package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.CMSDependencies
import com.example.aswcms.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(private val repository: AuthenticationRepository = CMSDependencies.authenticationRepository) :
    ViewModel() {
    val state: StateFlow<MainScreenState> = repository.currentStoreId.map { storeId ->
        if (storeId > 0) {
            MainScreenState.Overview(storeId)
        } else {
            MainScreenState.Stores
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MainScreenState.Loading)

    val menuItems = listOf(MainMenuItem.Account, MainMenuItem.Stores, MainMenuItem.Logout)


    fun onIntent(intent: MainScreenIntent) {
        when (intent) {
            is MainScreenIntent.RequestStoreOverView -> {
                viewModelScope.launch {
                    repository.saveCurrentStoreId(intent.storeId)
                }
            }

            MainScreenIntent.RequestStores -> {
                viewModelScope.launch {
                    repository.clearCurrentStore()
                }
            }

            MainScreenIntent.RequestLogout -> {
                viewModelScope.launch {
                    repository.saveIsLoggedIn(false)
                }
            }
        }
    }
}

sealed interface MainMenuItem {
    data object Account : MainMenuItem
    data object Stores : MainMenuItem
    data object Logout : MainMenuItem
}

sealed interface MainScreenState {

    object Loading: MainScreenState
    object Stores : MainScreenState
    data class Overview(val storeId: Int) : MainScreenState
}

sealed interface MainScreenIntent {
    object RequestStores : MainScreenIntent
    data class RequestStoreOverView(val storeId: Int) : MainScreenIntent

    object RequestLogout: MainScreenIntent
}