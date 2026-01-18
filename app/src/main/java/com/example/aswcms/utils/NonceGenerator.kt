package com.example.aswcms.utils

import java.security.SecureRandom
import java.util.Base64

object NonceGenerator {
    private val secureRandom = SecureRandom()

    fun generateSecureRandomNonce(byteLength: Int = 32): String {
        val randomBytes = ByteArray(byteLength)
        secureRandom.nextBytes(randomBytes)
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(randomBytes)
    }
}