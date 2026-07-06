package com.cjthdev.ganamnesis.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SteamOwnedGamesResponseWrapper(
    val response: SteamOwnedGamesResponse,
)

@Serializable
data class SteamOwnedGamesResponse(
    @SerialName("game_count") val gameCount: Int,
    val games: List<SteamGameDto> = emptyList(),
)

@Serializable
data class SteamGameDto(
    val appid: Int,
    val name: String,
    @SerialName("img_icon_url") val imgIconUrl: String? = null,
    @SerialName("playtime_forever") val playtimeForever: Int? = null,
)
