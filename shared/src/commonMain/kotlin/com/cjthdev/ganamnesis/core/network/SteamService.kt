package com.cjthdev.ganamnesis.core.network

import com.cjthdev.ganamnesis.core.network.dto.SteamGameDto
import com.cjthdev.ganamnesis.core.network.dto.SteamOwnedGamesResponseWrapper
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class SteamService(
    private val client: HttpClient,
) {
    private val baseUrl = "https://api.steampowered.com"

    suspend fun getOwnedGames(
        apiKey: String,
        steamId: String,
    ): List<SteamGameDto> {
        val response: SteamOwnedGamesResponseWrapper =
            client
                .get("$baseUrl/IPlayerService/GetOwnedGames/v1/") {
                    parameter("key", apiKey)
                    parameter("steamid", steamId)
                    parameter("include_appinfo", true)
                    parameter("format", "json")
                }.body()
        return response.response.games
    }
}
