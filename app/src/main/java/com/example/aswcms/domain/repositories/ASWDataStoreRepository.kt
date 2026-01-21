package com.example.aswcms.domain.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ASWDataStoreRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun isLoggedIn(): Boolean {
        return withContext(dispatcher) {
            false
        }
    }
}