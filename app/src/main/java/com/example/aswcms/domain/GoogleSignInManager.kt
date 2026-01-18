package com.example.aswcms.domain

import android.content.Context
import android.os.Build
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.aswcms.extensions.isUserCancellation

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
            credentialManager.getCredential(
                request = request,
                context = context
            )
            SignInResult.Success

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
    data object Success : SignInResult
    data object CancelledByUser : SignInResult
    data class Failure(val cause: Throwable) : SignInResult
}
