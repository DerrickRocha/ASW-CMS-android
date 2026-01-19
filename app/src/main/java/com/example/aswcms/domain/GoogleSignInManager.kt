package com.example.aswcms.domain

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.aswcms.extensions.isUserCancellation
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

class GoogleSignInManager(
    private val request: GetCredentialRequest,
    private val credentialManager: CredentialManager
) {
    suspend fun signIn(
        context: Context,
    ): SignInResult {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return SignInResult.Failure(
                UnsupportedOperationException("API 34+ required")
            )
        }

        return try {
           /* val response = credentialManager.getCredential(
                request = request,
                context = context
            )
            val credential = response.credential
            when(credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            // Use googleIdTokenCredential and extract id to validate and
                            // authenticate on your server.
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)
                        } catch (e: GoogleIdTokenParsingException) {
                            Log.e(TAG, "Received an invalid google id token response", e)
                        }
                    } else {
                        // Catch any unrecognized credential type here.
                        Log.e(TAG, "Unexpected type of credential")
                    }
                }

                else -> {
                    // Catch any unrecognized credential type here.
                    Log.e(TAG, "Unexpected type of credential")
                }
            }*/
            SignInResult.Success("mock token")

        } catch (e: GetCredentialCancellationException) {
            if (e.isUserCancellation()) {
                SignInResult.CancelledByUser
            } else {
                SignInResult.Failure(e)
            }

        } catch (e: Exception) {
            SignInResult.Failure(e)
        }
    }
}

sealed interface SignInResult {
    data class Success(val token: String) : SignInResult
    data object CancelledByUser : SignInResult
    data class Failure(val cause: Throwable) : SignInResult
}
