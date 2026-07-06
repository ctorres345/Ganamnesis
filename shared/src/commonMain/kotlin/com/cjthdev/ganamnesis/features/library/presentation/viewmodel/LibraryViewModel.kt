package com.cjthdev.ganamnesis.features.library.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cjthdev.ganamnesis.core.common.BaseViewModel
import com.cjthdev.ganamnesis.core.data.repository.GameRepository
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val gameRepository: GameRepository,
) : BaseViewModel<LibraryState, LibraryIntent, LibraryEffect>() {
    override fun createInitialState(): LibraryState = LibraryState()

    override fun handleIntent(intent: LibraryIntent) {
        when (intent) {
            LibraryIntent.Load -> loadLibrary()
        }
    }

    private fun loadLibrary() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            gameRepository.getLibrary().collect { games ->
                setState { copy(isLoading = false, games = games) }
            }
        }
    }
}
