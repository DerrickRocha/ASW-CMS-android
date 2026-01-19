package com.example.aswcms

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.aswcms.domain.GoogleSignInManager
import com.example.aswcms.domain.repositories.AuthenticationRepository
import com.example.aswcms.utils.NonceGenerator.generateSecureRandomNonce
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption

object CMSDependencies {
    private lateinit var application: AswCmsApplication
    fun init(applicationContext: AswCmsApplication){
        application = applicationContext
    }

    val googleManager: GoogleSignInManager by lazy {
        val option = GetSignInWithGoogleOption.Builder("webClientId")
            .setNonce(generateSecureRandomNonce())
            .build()

        val credentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()
        val credentialManager = CredentialManager.create(application)

        GoogleSignInManager(credentialRequest, credentialManager)
    }

    val authenticationRepository: AuthenticationRepository by lazy {
        AuthenticationRepository()
    }
}
