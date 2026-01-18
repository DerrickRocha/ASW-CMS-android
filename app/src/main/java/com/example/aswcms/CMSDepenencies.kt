package com.example.aswcms

import androidx.credentials.GetCredentialRequest
import com.example.aswcms.repositories.LoginRepository
import com.example.aswcms.ui.login.generateSecureRandomNonce
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption

val loginRepository: LoginRepository by lazy {
    val option = GetSignInWithGoogleOption.Builder("webClientId")
        .setNonce(generateSecureRandomNonce())
        .build()

    val credentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(option)
        .build()

    LoginRepository(credentialRequest)
}