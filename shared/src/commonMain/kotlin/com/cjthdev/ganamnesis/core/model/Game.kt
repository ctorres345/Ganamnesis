package com.cjthdev.ganamnesis.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: String,
    val title: String,
    val coverUrl: String? = null,
    val description: String? = null,
    val rating: Double? = null,
    val releaseDate: String? = null,
    val platforms: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val developers: List<String> = emptyList(),
    val publishers: List<String> = emptyList(),
    val steamId: String? = null,
    val rawgId: String? = null,
    val franchise: String? = null,
    val hasRetroAchievements: Boolean = false
)
