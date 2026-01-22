package com.example.aswcms.domain.repositories

import com.example.aswcms.domain.models.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoresRepository(val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun loadStores(): List<Store> {
        return withContext(dispatcher) {
            listOf(
                Store("Smiling Moon Farm", 1),
                Store("Jurassic Pets", 2)
            )
        }
    }
}