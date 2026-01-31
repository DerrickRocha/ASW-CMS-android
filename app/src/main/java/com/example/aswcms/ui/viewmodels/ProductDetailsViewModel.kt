package com.example.aswcms.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.domain.repositories.ProductRepository
import com.example.aswcms.extensions.toOptionChoicesHeadline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    /* ---------- UI STATE UPDATES ---------- */

    fun onNameChange(name: String) {
        _state.update { it.copy(productName = name) }
    }

    fun onDescriptionChange(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onPriceChange(priceString: String) {
        updatePriceField(priceString) { text , cents ->
            copy(
                priceString = text,
                price = cents,
                isPriceValid = cents != null
            )
        }
    }

    fun onSalePriceChange(salePriceString: String) {
        updatePriceField(salePriceString) { text: String, cents: Int? ->
            copy(
                salePriceString = text,
                price = cents,
                isSalePriceValid = cents != null
            )
        }
    }

    private fun updatePriceField(
        text: String,
        update: ProductDetailsState.(String, Int?) -> ProductDetailsState
    ) {
        val parsed = text.toCleanAndParsedInt()
        _state.update { it.update(text, parsed) }
    }


    fun onActiveChange(isActive: Boolean) {
        _state.update { it.copy(isActive = isActive) }
    }

    fun onTrackInventoryChange(track: Boolean) {
        _state.update { it.copy(trackInventory = track) }
    }

    fun onInventoryQuantityChange(inventoryQuantityText: String) {
        updatePriceField(inventoryQuantityText) { text: String, value: Int? ->
            copy(inventoryQuantityText = text, inventoryCount = value, isQuantityValid = value != null)
        }
    }

    /* ---------- USER ACTIONS ---------- */

    fun saveProduct() {
        viewModelScope.launch {
            // validation, mapping, repository call
        }
    }

    private fun loadProduct() {
        viewModelScope.launch {
            val product = repository.getProduct(productId)
            _state.update {
                it.copy(
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
}

private fun String.toCleanAndParsedInt(): Int? {
    if (this.any { !it.isDigit() }) return null
    return this.toIntOrNull()
}

data class ProductDetailsState(
    val productName: String = "",
    val description: String = "",
    val priceString: String = "",
    val price: Int? = null,
    val isPriceValid: Boolean = true,
    val salePriceString: String = "",
    val salePrice: Int? = null,
    val isSalePriceValid: Boolean = true,
    val isActive: Boolean = false,
    val trackInventory: Boolean = false,
    val isEditing: Boolean = false,
    val options: List<OptionState> = emptyList(),
    val inventoryQuantityText: String = "",
    val inventoryCount: Int? = null,
    val isQuantityValid: Boolean = true,
    val mainImage: ImageState? = null,
    val otherImages: List<ImageState> = emptyList()
)

data class OptionState(val name: String = "", val optionsString:String = "")

data class ImageState(val url: Uri? = null, val imageId: Int? = null, val productId: Int)