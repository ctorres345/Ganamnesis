package com.cjthdev.ganamnesis.core.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Marker interface for UI State
 */
interface UiState

/**
 * Marker interface for UI Intents (User Actions)
 */
interface UiIntent

/**
 * Marker interface for UI Effects (One-off events like navigation, toasts)
 */
interface UiEffect

/**
 * Base ViewModel for MVI architecture (KMP compatible)
 */
abstract class BaseViewModel<S : UiState, I : UiIntent, E : UiEffect> : ViewModel() {

    private val initialState: S by lazy { createInitialState() }
    abstract fun createInitialState(): S

    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _intent: MutableSharedFlow<I> = MutableSharedFlow()
    val intent = _intent.asSharedFlow()

    private val _effect: Channel<E> = Channel()
    val effect = _effect.receiveAsFlow()

    val currentState: S
        get() = uiState.value

    init {
        subscribeIntents()
    }

    private fun subscribeIntents() {
        viewModelScope.launch {
            intent.collect {
                handleIntent(it)
            }
        }
    }

    abstract fun handleIntent(intent: I)

    fun setIntent(intent: I) {
        val newIntent = intent
        viewModelScope.launch { _intent.emit(newIntent) }
    }

    protected fun setState(reduce: S.() -> S) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> E) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}
