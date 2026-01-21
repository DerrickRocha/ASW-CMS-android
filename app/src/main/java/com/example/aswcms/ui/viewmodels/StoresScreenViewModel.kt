package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.aswcms.domain.models.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StoresScreenViewModel: ViewModel() {

    private val _state = MutableStateFlow<StoresScreenState>(StoresScreenState())
    val state: StateFlow<StoresScreenState> = _state
}

data class StoresScreenState(
    val isLoading: Boolean = false,
    val stores: List<Store> = emptyList(),
    val error: String? = null
)
