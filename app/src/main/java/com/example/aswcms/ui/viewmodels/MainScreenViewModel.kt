package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.CMSDependencies
import com.example.aswcms.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(private val repository: AuthenticationRepository = CMSDependencies.authenticationRepository): ViewModel() {
    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Stores)
    val state: StateFlow<MainScreenState> = _state

    init{
        viewModelScope.launch {
            repository.currentStoreId.collect { storeId ->
                _state.value = if(storeId > 0) MainScreenState.Overview(storeId) else MainScreenState.Stores
            }
        }
    }

    fun onIntent(intent: MainScreenIntent) {
        when(intent) {
            is MainScreenIntent.RequestStoreOverView -> {
                _state.value = MainScreenState.Overview(intent.storeId)
                viewModelScope.launch {
                    repository.saveCurrentStoreId(intent.storeId)
                }
            }
            MainScreenIntent.RequestStores -> {
                _state.value = MainScreenState.Stores
            }
        }
    }
}

sealed interface MainScreenState {
    object Stores: MainScreenState
    data class Overview(val storeId: Int): MainScreenState
}

sealed interface MainScreenIntent {
    object RequestStores: MainScreenIntent
    data class RequestStoreOverView(val storeId: Int): MainScreenIntent
}