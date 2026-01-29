package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.domain.repositories.ProductRepository
import com.example.aswcms.extensions.toOptionChoicesHeadline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val repository: ProductRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailsState())
    val state: StateFlow<ProductDetailsState> = _state

    private val productId: Int =
        handle["productId"]
            ?: -1

    init {
        if (productId > 0) loadProduct()
    }

    fun onIntent(intent: ProductDetailsIntent) {
        when(intent) {
            is ProductDetailsIntent.TextFieldIntent -> handleTextFieldIntent(intent)
        }
    }

    private fun handleTextFieldIntent(intent: ProductDetailsIntent.TextFieldIntent) {
        when(intent.event) {
            is ProductDetailsTextFieldEvent.ActiveChanged -> TODO()
            is ProductDetailsTextFieldEvent.DescriptionChanges -> TODO()
            is ProductDetailsTextFieldEvent.NameChanged -> TODO()
            is ProductDetailsTextFieldEvent.PriceChanged -> TODO()
            is ProductDetailsTextFieldEvent.TrackInventoryChanged -> TODO()
        }
    }

    private fun loadProduct() {
        viewModelScope.launch {
            val product = repository.getProduct(productId)
            _state.value = _state.value.copy(
                productName = product.name,
                description = product.description,
                price = product.basePrice,
                isActive = product.isActive,
                trackInventory = false,
                isEditing = true,
                options = product.options.map { OptionState(name = it.name, optionsString = it.toOptionChoicesHeadline()) }
            )
        }
    }
}

sealed interface ProductDetailsIntent {
    data class TextFieldIntent(val event: ProductDetailsTextFieldEvent): ProductDetailsIntent
}

sealed interface ProductDetailsTextFieldEvent {
    data class NameChanged(val name: String): ProductDetailsTextFieldEvent
    data class DescriptionChanges(val description: String): ProductDetailsTextFieldEvent
    data class PriceChanged(val price: Int): ProductDetailsTextFieldEvent
    data class ActiveChanged(val isActive: Boolean): ProductDetailsTextFieldEvent
    data class TrackInventoryChanged(val isTracking: Boolean): ProductDetailsTextFieldEvent
}

data class ProductDetailsState(
    val productName: String = "",
    val description: String = "",
    val price: Int = 0,
    val isActive: Boolean = false,
    val trackInventory: Boolean = false,
    val isEditing: Boolean = false,
    val options: List<OptionState> = emptyList()
)

data class OptionState(val name: String = "", val optionsString:String = "")