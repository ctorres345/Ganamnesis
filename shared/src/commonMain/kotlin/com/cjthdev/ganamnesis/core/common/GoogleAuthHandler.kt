package com.cjthdev.ganamnesis.core.common

/**
 * Interface for platform-specific google sign-in implementations
 */
interface GoogleAuthHandler {
    /**
     * This should trigger the native sign-in UI and return a tokenId or null if canceled
     */
    suspend fun signIn(): GoogleSignInResult
}

sealed class GoogleSignInResult {
    data class Success(
        val idToken: String,
    ) : GoogleSignInResult()

    data class Failure(
        val message: String,
    ) : GoogleSignInResult()

    object Cancelled : GoogleSignInResult()
}
