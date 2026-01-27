package com.example.aswcms.domain.repositories

import com.example.aswcms.domain.models.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoresRepository @Inject constructor() {

    suspend fun loadStores(): List<Store> {
        return withContext(Dispatchers.IO) {
            listOf(
                Store("Smiling Moon Farm", 1),
                Store("Jurassic Pets", 2)
            )
        }
    }
}