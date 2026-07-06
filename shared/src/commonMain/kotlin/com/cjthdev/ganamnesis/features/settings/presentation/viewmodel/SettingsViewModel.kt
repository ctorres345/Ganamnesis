package com.cjthdev.ganamnesis.features.settings.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cjthdev.ganamnesis.core.common.BaseViewModel
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<SettingsState, SettingsIntent, SettingsEffect>() {
    override fun createInitialState(): SettingsState = SettingsState()

    override fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.Logout -> performLogout()
        }
    }

    private fun performLogout() {
        viewModelScope.launch {
            setState { copy(isLoggingOut = true) }
            authRepository.logout()
            setState { copy(isLoggingOut = false) }
            setEffect { SettingsEffect.NavigateToLogin }
        }
    }
}
