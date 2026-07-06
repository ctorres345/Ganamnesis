package com.cjthdev.ganamnesis.features.profile.presentation.viewmodel

import com.cjthdev.ganamnesis.core.common.UiEffect
import com.cjthdev.ganamnesis.core.common.UiIntent
import com.cjthdev.ganamnesis.core.common.UiState
import com.cjthdev.ganamnesis.core.model.User

sealed class ProfileIntent : UiIntent {
    object Load : ProfileIntent()
}

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
) : UiState

sealed class ProfileEffect : UiEffect
