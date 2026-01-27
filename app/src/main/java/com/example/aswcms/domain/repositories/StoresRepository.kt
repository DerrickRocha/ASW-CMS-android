package com.example.aswcms.domain.repositories

import com.example.aswcms.di.IoDispatcher
import com.example.aswcms.domain.models.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoresRepository @Inject constructor(
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun loadStores(): List<Store> {
        return withContext(dispatcher) {
            listOf(
                Store("Smiling Moon Farm", 1),
                Store("Jurassic Pets", 2)
            )
        }
    }
}