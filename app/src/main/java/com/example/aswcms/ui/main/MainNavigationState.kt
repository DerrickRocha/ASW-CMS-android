package com.example.aswcms.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

class MainNavigationState(currentKey: NavKey) {

    @Serializable
    data object Loading: NavKey

    @Serializable
    data object Stores: NavKey

    @Serializable
    data class StoreOverview(val storeId: Int): NavKey

    @Serializable
    data object Orders: NavKey

    @Serializable
    data class Products(val storeId: Int): NavKey

    @Serializable
    data class Product(val productId: Int): NavKey

    @Serializable
    data object Customers: NavKey

    @Serializable
    data object Inventory: NavKey

    val backstack = mutableStateListOf<NavKey>(currentKey)

    val current: NavKey?
        get() = backstack.lastOrNull()

    val isTopLevel: Boolean
        get() = current is Stores || current is StoreOverview

    fun navigate(key: NavKey) {
        backstack.add(key)
    }

    fun navigateUp() {
        backstack.removeLastOrNull()
    }

    fun resetTo(key: NavKey) {
        backstack.clear()
        backstack.add(key)
    }

}