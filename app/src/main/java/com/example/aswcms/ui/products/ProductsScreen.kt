package com.example.aswcms.ui.products

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.aswcms.ui.components.LoadingSection
import com.example.aswcms.ui.viewmodels.ProductState
import com.example.aswcms.ui.viewmodels.ProductsScreenState
import com.example.aswcms.ui.viewmodels.ProductsScreenViewModel

@Composable
fun ProductsScreen(
    storeId: Int? = null,
    viewModel: ProductsScreenViewModel = hiltViewModel(),
    onProductSelected: (productId: Int) -> Unit
) {
    LaunchedEffect(storeId) {
        storeId?.let {
            viewModel.loadProducts(it)
        }
    }
    val state by viewModel.state.collectAsState()
    ProductsScreenContent(state, onProductSelected)
}

@Composable
fun ProductsScreenContent(
    state: ProductsScreenState,
    onProductSelected: (productId: Int) -> Unit = {}
) {
    when(state) {
        ProductsScreenState.Error -> {
            ProductsScreenError()
        }
        ProductsScreenState.Loading -> {
            LoadingSection()
        }
        is ProductsScreenState.Products -> {
            ProductSection(state.products, onProductSelected)
        }
    }

}

@Composable
fun ProductsScreenError() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error loading products. Please try again later")
    }
}

@Composable
fun ProductSection(products: List<ProductState>, onProductSelected: (productId: Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(products) { product ->
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onProductSelected(product.productId) }),
                leadingContent = {
                    ASWCMSImage(
                        modifier = Modifier.size(50.dp),
                        image = Uri.parse(product.imageUrl),
                        description = ""
                    )
                }, headlineContent = {
                    Text(product.name)
                }
            )
        }
    }
}