package com.cjthdev.ganamnesis.features.setup.presentation.viewmodel

import com.cjthdev.ganamnesis.core.common.UiEffect
import com.cjthdev.ganamnesis.core.common.UiIntent
import com.cjthdev.ganamnesis.core.common.UiState
import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.model.SyncStatus

sealed class SetupIntent : UiIntent {
    data class UpdateSteamKeys(val key: String, val id: String) : SetupIntent()
    data class UpdateRawgKey(val key: String) : SetupIntent()
    object StartSteamSync : SetupIntent()
    data class SearchGames(val query: String) : SetupIntent()
    data class AddGame(val game: Game) : SetupIntent()
    object CompleteSetup : SetupIntent()
}

data class SetupState(
    val isLoading: Boolean = false,
    val steamKey: String = "",
    val steamId: String = "",
    val rawgKey: String = "",
    val syncStatus: SyncStatus? = null,
    val searchResults: List<Game> = emptyList(),
    val addedGames: List<Game> = emptyList(),
    val error: String? = null
) : UiState

sealed class SetupEffect : UiEffect {
    object NavigateToDashboard : SetupEffect()
    data class ShowError(val message: String) : SetupEffect()
}
