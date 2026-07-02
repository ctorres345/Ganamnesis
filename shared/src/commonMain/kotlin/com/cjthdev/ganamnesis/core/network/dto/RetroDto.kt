package com.cjthdev.ganamnesis.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RetroUserSummaryDto(
    @SerialName("User") val user: String,
    @SerialName("Motto") val motto: String? = null,
    @SerialName("TotalPoints") val totalPoints: Int? = null,
    @SerialName("TotalTruePoints") val totalTruePoints: Int? = null,
    @SerialName("UserPic") val userPic: String? = null
)
