package com.example.aswcms.domain.repositories

import com.example.aswcms.domain.models.LoginSuccessData
import com.example.aswcms.domain.models.User

class AuthenticationRepository {

    fun login(): AuthenticationResult {
        return AuthenticationResult.LoginSuccess(
            LoginSuccessData(
                User("Derrick", "Rocha"),
                "fjkdafsjdskfjlkdsjfklsdj="
            )
        )
    }
}


sealed interface AuthenticationResult{
    data class LoginSuccess(val data: LoginSuccessData): AuthenticationResult
}