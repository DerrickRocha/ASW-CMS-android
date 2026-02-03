package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.domain.repositories.AuthenticationRepository
import com.example.aswcms.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: AuthenticationRepository) :
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
    val route: String?
    data object Account : MainMenuItem {
        override val route = Routes.ACCOUNT
    }

    data object Stores : MainMenuItem {
        override val route = Routes.STORES
    }

    data object Logout : MainMenuItem{
        override val route: String? = null
    }
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