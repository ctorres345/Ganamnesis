package com.cjthdev.ganamnesis.core.network

import com.cjthdev.ganamnesis.core.network.dto.RawgGameDto
import com.cjthdev.ganamnesis.core.network.dto.RawgSearchResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RawgService(private val client: HttpClient) {
    private val baseUrl = "https://api.rawg.io/api"

    suspend fun searchGames(query: String, apiKey: String): List<RawgGameDto> {
        val response: RawgSearchResponse = client.get("$baseUrl/games") {
            parameter("key", apiKey)
            parameter("search", query)
            parameter("page_size", 20)
        }.body()
        return response.results
    }

    suspend fun getGameDetails(id: Int, apiKey: String): RawgGameDto {
        return client.get("$baseUrl/games/$id") {
            parameter("key", apiKey)
        }.body()
    }
}
