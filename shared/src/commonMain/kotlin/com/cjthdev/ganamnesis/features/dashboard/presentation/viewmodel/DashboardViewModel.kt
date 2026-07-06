package com.cjthdev.ganamnesis.features.dashboard.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cjthdev.ganamnesis.core.common.BaseViewModel
import com.cjthdev.ganamnesis.core.data.repository.GameRepository
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val authRepository: AuthRepository,
    private val gameRepository: GameRepository,
) : BaseViewModel<DashboardState, DashboardIntent, DashboardEffect>() {
    override fun createInitialState(): DashboardState = DashboardState()

    override fun handleIntent(intent: DashboardIntent) {
        when (intent) {
            DashboardIntent.Load -> loadDashboard()
        }
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            combine(
                authRepository.getCurrentUser(),
                gameRepository.getLibrary(),
            ) { user, games -> user to games.size }
                .collect { (user, libraryCount) ->
                    setState { copy(isLoading = false, user = user, libraryCount = libraryCount) }
                }
        }
    }
}
