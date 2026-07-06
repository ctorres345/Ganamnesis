package com.cjthdev.ganamnesis.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: String,
    val title: String,
    val coverUrl: String? = null,
    val description: String? = null,
    val rating: Double? = null,
    val releaseDate: String? = null,
    val platforms: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val developers: List<String> = emptyList(),
    val publishers: List<String> = emptyList(),
    val screenshots: List<String> = emptyList(),
    val steamId: String? = null,
    val igdbId: String? = null,
    val franchise: String? = null,
    val hasRetroAchievements: Boolean = false,
)
