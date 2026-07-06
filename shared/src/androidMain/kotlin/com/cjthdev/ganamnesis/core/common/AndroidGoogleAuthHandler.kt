package com.cjthdev.ganamnesis.core.common

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class AndroidGoogleAuthHandler(
    private val context: Context,
    private val webClientId: String,
) : GoogleAuthHandler {
    private val credentialManager = CredentialManager.create(context)

    override suspend fun signIn(): GoogleSignInResult {
        val googleIdTokenOption =
            GetGoogleIdOption
                .Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(true)
                .build()

        val request =
            GetCredentialRequest
                .Builder()
                .addCredentialOption(googleIdTokenOption)
                .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
            GoogleSignInResult.Success(googleIdTokenCredential.idToken)
        } catch (e: GetCredentialException) {
            handleException(e)
        } catch (e: Exception) {
            println("Google Auth: Unexpected error: ${e.message}")
            GoogleSignInResult.Cancelled
        }
    }

    private fun handleException(e: GetCredentialException): GoogleSignInResult =
        when (e) {
            is GetCredentialCancellationException -> {
                println("Google Auth: User cancelled the sign-in flow.")
                GoogleSignInResult.Cancelled
            }

            is NoCredentialException -> {
                val errorMessage =
                    "Google Auth: No accounts found. User needs to add a Google account to device settings."
                println(errorMessage)
                GoogleSignInResult.Failure(errorMessage)
            }

            else -> {
                val errorMessage = "Google Auth: Error type: ${e.type}, Message: ${e.message}"
                println(errorMessage)
                GoogleSignInResult.Failure(errorMessage)
            }
        }
}
