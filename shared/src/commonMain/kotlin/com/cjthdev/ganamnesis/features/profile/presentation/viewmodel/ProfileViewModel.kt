package com.cjthdev.ganamnesis.features.profile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cjthdev.ganamnesis.core.common.BaseViewModel
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<ProfileState, ProfileIntent, ProfileEffect>() {
    override fun createInitialState(): ProfileState = ProfileState()

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.Load -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            authRepository.getCurrentUser().collect { user ->
                setState { copy(isLoading = false, user = user) }
            }
        }
    }
}
