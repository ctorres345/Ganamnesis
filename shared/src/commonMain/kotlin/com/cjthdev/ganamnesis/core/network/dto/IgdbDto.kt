package com.cjthdev.ganamnesis.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class IgdbGameDto(
    val id: Int,
    val name: String,
    val cover: IgdbImageDto? = null,
    val screenshots: List<IgdbImageDto>? = emptyList(),
    val rating: Double? = null,
    val first_release_date: Long? = null,
    val platforms: List<IgdbNamedDto>? = emptyList(),
    val genres: List<IgdbNamedDto>? = emptyList(),
    val involved_companies: List<IgdbInvolvedCompanyDto>? = emptyList(),
)

@Serializable
data class IgdbImageDto(
    val image_id: String,
)

@Serializable
data class IgdbNamedDto(
    val id: Int,
    val name: String,
)

@Serializable
data class IgdbInvolvedCompanyDto(
    val company: IgdbNamedDto,
    val developer: Boolean = false,
    val publisher: Boolean = false,
)
