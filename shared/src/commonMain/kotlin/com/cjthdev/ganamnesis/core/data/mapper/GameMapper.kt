package com.cjthdev.ganamnesis.core.data.mapper

import com.cjthdev.ganamnesis.core.database.entity.GameEntity
import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.network.dto.IgdbGameDto
import com.cjthdev.ganamnesis.core.network.dto.SteamGameDto

fun IgdbGameDto.toDomain(): Game =
    Game(
        id = id.toString(),
        title = name,
        coverUrl = cover?.image_id?.let { "https://images.igdb.com/igdb/image/upload/t_cover_big/$it.jpg" },
        screenshots =
            screenshots?.map { "https://images.igdb.com/igdb/image/upload/t_screenshot_big/${it.image_id}.jpg" }
                ?: emptyList(),
        rating = rating,
        releaseDate = first_release_date?.let { epochSecondsToIsoDate(it) },
        platforms = platforms?.map { it.name } ?: emptyList(),
        genres = genres?.map { it.name } ?: emptyList(),
        developers = involved_companies?.filter { it.developer }?.map { it.company.name } ?: emptyList(),
        publishers = involved_companies?.filter { it.publisher }?.map { it.company.name } ?: emptyList(),
        igdbId = id.toString(),
    )

fun SteamGameDto.toDomain(): Game =
    Game(
        id = appid.toString(),
        title = name,
        coverUrl =
            if (imgIconUrl !=
                null
            ) {
                "https://media.steampowered.com/steamcommunity/public/images/apps/$appid/$imgIconUrl.jpg"
            } else {
                null
            },
        steamId = appid.toString(),
    )

fun GameEntity.toDomain(): Game =
    Game(
        id = id,
        title = title,
        coverUrl = coverUrl,
        description = description,
        rating = rating,
        releaseDate = releaseDate,
        platforms = platforms,
        genres = genres,
        developers = developers,
        publishers = publishers,
        screenshots = screenshots,
        steamId = steamId,
        igdbId = igdbId,
        franchise = franchise,
        hasRetroAchievements = hasRetroAchievements,
    )

fun Game.toEntity(): GameEntity =
    GameEntity(
        id = id,
        title = title,
        coverUrl = coverUrl,
        description = description,
        rating = rating,
        releaseDate = releaseDate,
        platforms = platforms,
        genres = genres,
        developers = developers,
        publishers = publishers,
        screenshots = screenshots,
        steamId = steamId,
        igdbId = igdbId,
        franchise = franchise,
        hasRetroAchievements = hasRetroAchievements,
    )

/** Dependency-free Unix-epoch-seconds -> "yyyy-MM-dd" conversion (Howard Hinnant's civil_from_days algorithm). */
private fun epochSecondsToIsoDate(epochSeconds: Long): String {
    val days = epochSeconds / 86400
    val z = days + 719468
    val era = if (z >= 0) z else z - 146096
    val eraDiv = era / 146097
    val doe = era - eraDiv * 146097
    val yoe = (doe - doe / 1460 + doe / 36524 - doe / 146096) / 365
    val y = yoe + eraDiv * 400
    val doy = doe - (365 * yoe + yoe / 4 - yoe / 100)
    val mp = (5 * doy + 2) / 153
    val d = doy - (153 * mp + 2) / 5 + 1
    val m = if (mp < 10) mp + 3 else mp - 9
    val year = if (m <= 2) y + 1 else y
    return "${year.toString().padStart(4, '0')}-${m.toString().padStart(2, '0')}-${d.toString().padStart(2, '0')}"
}
