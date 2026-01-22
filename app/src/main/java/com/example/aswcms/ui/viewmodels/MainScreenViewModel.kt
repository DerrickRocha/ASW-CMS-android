package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel(): ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Stores)
    val state: StateFlow<HomeScreenState> = _state

    init{
        //todo: Load state from data store repository.
    }

    fun onIntent(intent: HomeScreenIntent) {
        when(intent) {
            is HomeScreenIntent.RequestStoreOverView -> _state.value = HomeScreenState.Overview(intent.storeId)
            HomeScreenIntent.RequestStores -> _state.value = HomeScreenState.Stores
        }
    }
}

sealed interface HomeScreenState {
    object Stores: HomeScreenState
    data class Overview(val storeId: Int): HomeScreenState
}

sealed interface HomeScreenIntent {
    object RequestStores: HomeScreenIntent
    data class RequestStoreOverView(val storeId: Int): HomeScreenIntent
}