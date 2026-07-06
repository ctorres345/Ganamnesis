package com.cjthdev.ganamnesis.features.settings.presentation.viewmodel

import com.cjthdev.ganamnesis.core.common.UiEffect
import com.cjthdev.ganamnesis.core.common.UiIntent
import com.cjthdev.ganamnesis.core.common.UiState

sealed class SettingsIntent : UiIntent {
    object Logout : SettingsIntent()
}

data class SettingsState(
    val isLoggingOut: Boolean = false,
) : UiState

sealed class SettingsEffect : UiEffect {
    object NavigateToLogin : SettingsEffect()
}
