package com.example.aswcms.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

class MainNavigationState(state: NavKey) {

    @Serializable
    data object Stores: NavKey

    @Serializable
    data class StoreOverview(val storeId: Int): NavKey

    @Serializable
    data object Orders: NavKey

    @Serializable
    data object Products: NavKey

    @Serializable
    data object Customers: NavKey

    @Serializable
    data object Inventory: NavKey


    val backstack = mutableStateListOf(state)

    val current = backstack.last()

    val isTopLevel = current is Stores || current is StoreOverview

    fun navigate(key: NavKey) {
        backstack.add(key)
    }

    fun navigateUp() {
        backstack.removeLast()
    }

}