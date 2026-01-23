package com.example.aswcms

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.aswcms.domain.GoogleSignInManager
import com.example.aswcms.domain.repositories.AuthenticationRepository
import com.example.aswcms.domain.repositories.StoresRepository
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
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val authenticationRepository: AuthenticationRepository by lazy {
        AuthenticationRepository(application.dataStore)
    }
    val storesRepository: StoresRepository by lazy {
        StoresRepository()
    }

}
