package com.example.aswcms.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.aswcms.domain.GoogleSignInManager
import com.example.aswcms.utils.NonceGenerator.generateSecureRandomNonce
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile("settings") }
        )
    }

    @Provides
    @Singleton
    fun provideGoogleManager(@ApplicationContext appContext: Context): GoogleSignInManager {
        val option = GetSignInWithGoogleOption.Builder("webClientId")
            .setNonce(generateSecureRandomNonce())
            .build()

        val credentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()
        val credentialManager = CredentialManager.create(appContext)

        return GoogleSignInManager(credentialRequest, credentialManager)
    }
}