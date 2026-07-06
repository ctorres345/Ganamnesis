package com.cjthdev.ganamnesis.core.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

/**
 * Twitch OAuth2 client-credentials flow for IGDB. IGDB auth is app-level
 * (client ID + secret), not a per-user key, so a single cached token is
 * shared across all requests and refreshed transparently on expiry.
 */
class IgdbAuthTokenProvider(
    val clientId: String,
    private val clientSecret: String,
    private val client: HttpClient,
) {
    private val mutex = Mutex()
    private var cachedToken: String? = null
    private var expiryMark: TimeSource.Monotonic.ValueTimeMark? = null

    suspend fun getValidToken(): String =
        mutex.withLock {
            val token = cachedToken
            val expiry = expiryMark
            if (token != null && expiry != null && !expiry.hasPassedNow()) {
                return@withLock token
            }

            val response: TwitchTokenResponse =
                client
                    .post("https://id.twitch.tv/oauth2/token") {
                        parameter("client_id", clientId)
                        parameter("client_secret", clientSecret)
                        parameter("grant_type", "client_credentials")
                    }.body()

            cachedToken = response.access_token
            expiryMark = TimeSource.Monotonic.markNow() + (response.expires_in - EXPIRY_BUFFER_SECONDS).seconds
            response.access_token
        }

    private companion object {
        const val EXPIRY_BUFFER_SECONDS = 60L
    }
}

@Serializable
private data class TwitchTokenResponse(
    val access_token: String,
    val expires_in: Long,
    val token_type: String,
)
