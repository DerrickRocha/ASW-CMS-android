package com.example.aswcms.extensions

import androidx.credentials.exceptions.GetCredentialCancellationException

fun GetCredentialCancellationException.isUserCancellation(): Boolean {
    val message = message.orEmpty()
    return message.contains("Cancelled by user", ignoreCase = true)
}