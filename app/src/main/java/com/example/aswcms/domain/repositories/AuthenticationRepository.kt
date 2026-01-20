package com.example.aswcms.domain.repositories

import com.example.aswcms.domain.models.LoginSuccessData
import com.example.aswcms.domain.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun login(token: String): AuthenticationResult {
        return withContext(dispatcher) {
            AuthenticationResult.LoginSuccess(
                LoginSuccessData(
                    User("Derrick", "Rocha"),
                    "fjkdafsjdskfjlkdsjfklsdj="
                )
            )
        }
    }
}


sealed interface AuthenticationResult{
    data class LoginSuccess(val data: LoginSuccessData): AuthenticationResult

    data class LoginFailure(val message: String): AuthenticationResult
}