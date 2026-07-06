package com.cjthdev.ganamnesis.features.setup.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cjthdev.ganamnesis.core.common.BaseViewModel
import com.cjthdev.ganamnesis.core.data.repository.GameRepository
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SetupViewModel(
    private val authRepository: AuthRepository,
    private val gameRepository: GameRepository,
) : BaseViewModel<SetupState, SetupIntent, SetupEffect>() {
    override fun createInitialState(): SetupState = SetupState()

    override fun handleIntent(intent: SetupIntent) {
        when (intent) {
            is SetupIntent.UpdateSteamKeys -> {
                setState { copy(steamKey = intent.key, steamId = intent.id) }
                saveKeys()
            }
            is SetupIntent.StartSteamSync -> performSteamSync()
            is SetupIntent.LoadSteamCredentials -> loadSteamCredentials()
            is SetupIntent.SearchGames -> performSearch(intent.query)
            is SetupIntent.AddGame -> performAddGame(intent)
            is SetupIntent.CompleteSetup -> completeSetup()
        }
    }

    private fun completeSetup() {
        viewModelScope.launch {
            authRepository.getCurrentUser().first()?.let { user ->
                authRepository.updateUser(user.copy(hasCompletedSetup = true))
            }
            setEffect { SetupEffect.NavigateToDashboard }
        }
    }

    private fun saveKeys() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser().first()
            if (currentUser != null) {
                val updatedUser =
                    currentUser.copy(
                        steamKey = uiState.value.steamKey.ifBlank { currentUser.steamKey },
                        steamId = uiState.value.steamId.ifBlank { currentUser.steamId },
                    )
                authRepository.updateUser(updatedUser)
            }
        }
    }

    private fun loadSteamCredentials() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser().first()
            setState {
                copy(
                    steamKey = user?.steamKey.orEmpty(),
                    steamId = user?.steamId.orEmpty(),
                    hasExistingSteamCredentials = !user?.steamKey.isNullOrBlank(),
                    steamCredentialsLoaded = true,
                )
            }
        }
    }

    private fun performSteamSync() {
        viewModelScope.launch {
            setState { copy(steamSyncAttempted = true) }
            gameRepository.syncSteamLibrary().collect { status ->
                setState { copy(syncStatus = status) }
            }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            val result = gameRepository.searchGames(query)
            result
                .onSuccess { games ->
                    setState { copy(isLoading = false, searchResults = games) }
                }.onFailure { exception ->
                    setState { copy(isLoading = false, error = exception.message) }
                }
        }
    }

    private fun performAddGame(intent: SetupIntent.AddGame) {
        viewModelScope.launch {
            val result = gameRepository.addGame(intent.game)
            result.onSuccess {
                setState { copy(addedGames = addedGames + intent.game) }
            }
        }
    }
}
