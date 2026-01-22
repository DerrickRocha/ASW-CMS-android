package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainScreenViewModel(): ViewModel() {
    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Stores)
    val state: StateFlow<MainScreenState> = _state

    init{
        //todo: Load state from data store repository.
    }

    fun onIntent(intent: MainScreenIntent) {
        when(intent) {
            is MainScreenIntent.RequestStoreOverView -> _state.value = MainScreenState.Overview(intent.storeId)
            MainScreenIntent.RequestStores -> _state.value = MainScreenState.Stores
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