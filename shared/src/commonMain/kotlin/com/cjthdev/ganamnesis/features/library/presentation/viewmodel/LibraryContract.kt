package com.cjthdev.ganamnesis.features.library.presentation.viewmodel

import com.cjthdev.ganamnesis.core.common.UiEffect
import com.cjthdev.ganamnesis.core.common.UiIntent
import com.cjthdev.ganamnesis.core.common.UiState
import com.cjthdev.ganamnesis.core.model.Game

sealed class LibraryIntent : UiIntent {
    object Load : LibraryIntent()
}

data class LibraryState(
    val isLoading: Boolean = false,
    val games: List<Game> = emptyList(),
) : UiState

sealed class LibraryEffect : UiEffect
