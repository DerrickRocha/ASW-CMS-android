package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.CMSDependencies
import com.example.aswcms.domain.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddStoreScreenViewModel(val repository: AuthenticationRepository = CMSDependencies.authenticationRepository): ViewModel() {

    private val _state = MutableStateFlow(AddStoreScreenState())
    val state: StateFlow<AddStoreScreenState> = _state

    private val _events = MutableSharedFlow<AddStoreEvent>()
    val events: SharedFlow<AddStoreEvent> = _events

    fun onRequestAddStore() {
        _state.value = _state.value.copy(isSaving = true)
        viewModelScope.launch {
            delay(4_000)
            _state.value = _state.value.copy(isSaving = false)
            _events.emit(AddStoreEvent.Success)
        }
    }

    fun onStoreNameChanged(name: String) {
        _state.value = _state.value.copy(storeName = name)
    }

    fun onDomainNameChanged(domain: String) {
        _state.value = _state.value.copy(domain = domain)

    }

    fun onErrorDismissed() {
        _state.value = _state.value.copy(error = null)

    }
}

data class AddStoreScreenState(
    val storeName: String = "",
    val domain: String = "",
    val isSaving: Boolean = false,
    val error: AddStoreError? = null
)

sealed interface AddStoreEvent {
    data object Success: AddStoreEvent
}

data class AddStoreError(val message: String)

