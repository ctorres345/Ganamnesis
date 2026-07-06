package com.cjthdev.ganamnesis.features.dashboard.presentation.viewmodel

import com.cjthdev.ganamnesis.core.common.UiEffect
import com.cjthdev.ganamnesis.core.common.UiIntent
import com.cjthdev.ganamnesis.core.common.UiState
import com.cjthdev.ganamnesis.core.model.User

sealed class DashboardIntent : UiIntent {
    object Load : DashboardIntent()
}

data class DashboardState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val libraryCount: Int = 0,
    val error: String? = null,
) : UiState

sealed class DashboardEffect : UiEffect {
    data class ShowError(
        val message: String,
    ) : DashboardEffect()
}
