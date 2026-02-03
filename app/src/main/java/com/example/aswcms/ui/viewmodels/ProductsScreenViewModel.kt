package com.example.aswcms.ui.viewmodels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aswcms.domain.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val repository: ProductRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProductsScreenState>(ProductsScreenState.Loading)
    val state: StateFlow<ProductsScreenState> = _state
    private val storeId: Int =
        handle["storeId"]
            ?: error("storeId missing")

    init {
        loadProducts(storeId)
    }

    fun loadProducts(storeId: Int) {
        viewModelScope.launch {
            _state.value = ProductsScreenState.Loading
            val products = repository.getProductsForStore(storeId)
                .map { ProductState(it.id, it.name, it.imageUrl) }
            _state.value = ProductsScreenState.Products(products = products)
        }
    }
}

data class ProductState(val productId: Int, val name: String, val imageUrl: String)
sealed interface ProductsScreenState {
    data class Products(val products: List<ProductState>): ProductsScreenState
    data object Loading: ProductsScreenState
    data object Error: ProductsScreenState
}