package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.CMSDependencies
import com.example.aswcms.domain.models.Store
import com.example.aswcms.domain.repositories.StoresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoresScreenViewModel(private val repository: StoresRepository = CMSDependencies.storesRepository): ViewModel() {

    private val _state = MutableStateFlow(StoresScreenState())
    val state: StateFlow<StoresScreenState> = _state.asStateFlow()

    init{
        loadStores()
    }

    private fun loadStores() {
        viewModelScope.launch{
            val stores = repository.loadStores()
            _state.value = _state.value.copy(stores = stores)
        }
    }
}

data class StoresScreenState(
    val isLoading: Boolean = false,
    val stores: List<Store> = emptyList(),
    val errorMessage: String? = null,
)
