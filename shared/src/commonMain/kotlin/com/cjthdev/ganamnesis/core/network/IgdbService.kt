package com.cjthdev.ganamnesis.core.network

import com.cjthdev.ganamnesis.core.network.dto.IgdbGameDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class IgdbService(
    private val client: HttpClient,
    private val tokenProvider: IgdbAuthTokenProvider,
) {
    private val baseUrl = "https://api.igdb.com/v4"

    private val gameFields =
        "fields name,cover.image_id,screenshots.image_id,rating,first_release_date," +
            "platforms.name,genres.name,involved_companies.company.name,involved_companies.developer,involved_companies.publisher;"

    suspend fun searchGames(query: String): List<IgdbGameDto> = postApicalypse("""search "$query"; $gameFields limit 20;""")

    suspend fun getGameDetails(id: Int): IgdbGameDto? = postApicalypse("""where id = $id; $gameFields""").firstOrNull()

    private suspend fun postApicalypse(body: String): List<IgdbGameDto> {
        val token = tokenProvider.getValidToken()
        return client
            .post("$baseUrl/games") {
                header("Client-ID", tokenProvider.clientId)
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Text.Plain)
                setBody(body)
            }.body()
    }
}
