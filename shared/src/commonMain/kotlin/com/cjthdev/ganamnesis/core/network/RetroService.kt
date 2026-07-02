package com.cjthdev.ganamnesis.core.network

import com.cjthdev.ganamnesis.core.network.dto.RetroUserSummaryDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RetroService(private val client: HttpClient) {
    private val baseUrl = "https://retroachievements.org/API"

    suspend fun getUserSummary(user: String, apiKey: String): RetroUserSummaryDto {
        return client.get("$baseUrl/API_GetUserSummary.php") {
            parameter("z", user)
            parameter("y", apiKey)
            parameter("u", user)
        }.body()
    }
}
