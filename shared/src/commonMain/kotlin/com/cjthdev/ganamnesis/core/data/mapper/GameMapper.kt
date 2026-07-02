package com.cjthdev.ganamnesis.core.data.mapper

import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.network.dto.RawgGameDto
import com.cjthdev.ganamnesis.core.network.dto.SteamGameDto

fun RawgGameDto.toDomain(): Game {
    return Game(
        id = id.toString(),
        title = name,
        coverUrl = backgroundImage,
        rating = rating,
        releaseDate = released,
        platforms = platforms?.map { it.platform.name } ?: emptyList(),
        genres = genres?.map { it.name } ?: emptyList(),
        rawgId = id.toString()
    )
}

fun SteamGameDto.toDomain(): Game {
    return Game(
        id = appid.toString(),
        title = name,
        coverUrl = if (imgIconUrl != null) "https://media.steampowered.com/steamcommunity/public/images/apps/$appid/$imgIconUrl.jpg" else null,
        steamId = appid.toString()
    )
}
