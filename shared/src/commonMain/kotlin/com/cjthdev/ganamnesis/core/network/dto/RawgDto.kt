package com.cjthdev.ganamnesis.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawgSearchResponse(
    val count: Int,
    val results: List<RawgGameDto>
)

@Serializable
data class RawgGameDto(
    val id: Int,
    val name: String,
    @SerialName("background_image") val backgroundImage: String? = null,
    val rating: Double? = null,
    val released: String? = null,
    val description_raw: String? = null,
    val platforms: List<RawgPlatformParentDto>? = emptyList(),
    val genres: List<RawgGenreDto>? = emptyList()
)

@Serializable
data class RawgPlatformParentDto(
    val platform: RawgPlatformDto
)

@Serializable
data class RawgPlatformDto(
    val id: Int,
    val name: String,
    val slug: String
)

@Serializable
data class RawgGenreDto(
    val id: Int,
    val name: String
)
