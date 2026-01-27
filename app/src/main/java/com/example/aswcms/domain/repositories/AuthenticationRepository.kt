package com.example.aswcms.domain.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.aswcms.di.IoDispatcher
import com.example.aswcms.domain.models.LoginSuccessData
import com.example.aswcms.domain.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepository @Inject constructor(
    private val datastore: DataStore<Preferences>,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    val CURRENT_STORE_ID = intPreferencesKey("store_id")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    suspend fun login(token: String): AuthenticationResult {
        return withContext(dispatcher) {
            saveIsLoggedIn(true)
            AuthenticationResult.LoginSuccess(
                LoginSuccessData(
                    User("sam", "sneed"),
                    "fjkdafsjdskfjlkdsjfklsdj="
                )
            )
        }
    }

    val isLoggedIn = datastore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    suspend fun saveIsLoggedIn(isLoggedIn: Boolean) {
        datastore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    val currentStoreId = datastore.data.map { preferences ->
        preferences[CURRENT_STORE_ID] ?: -1
    }

    suspend fun saveCurrentStoreId(storeId: Int) {
        datastore.edit { preferences ->
            preferences[CURRENT_STORE_ID] = storeId
        }
    }

    suspend fun clearCurrentStore() {
        datastore.edit { preferences ->
            preferences.remove(CURRENT_STORE_ID)
        }
    }
}


sealed interface AuthenticationResult{
    data class LoginSuccess(val data: LoginSuccessData): AuthenticationResult

    data class LoginFailure(val message: String): AuthenticationResult
}