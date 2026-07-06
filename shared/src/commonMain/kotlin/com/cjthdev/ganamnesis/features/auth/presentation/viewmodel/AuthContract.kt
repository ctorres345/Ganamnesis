package com.cjthdev.ganamnesis.features.auth.presentation.viewmodel

import com.cjthdev.ganamnesis.core.common.UiEffect
import com.cjthdev.ganamnesis.core.common.UiIntent
import com.cjthdev.ganamnesis.core.common.UiState
import com.cjthdev.ganamnesis.core.model.User

sealed class AuthIntent : UiIntent {
    data class Login(
        val email: String,
        val password: String,
    ) : AuthIntent()

    data class SignUp(
        val email: String,
        val password: String,
        val username: String,
    ) : AuthIntent()

    data class SendPasswordReset(
        val email: String,
    ) : AuthIntent()

    object LoginWithGoogle : AuthIntent()

    object Logout : AuthIntent()
}

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isPasswordResetSent: Boolean = false,
) : UiState

sealed class AuthEffect : UiEffect {
    data class ShowError(
        val message: String,
    ) : AuthEffect()

    object NavigateToHome : AuthEffect()

    object NavigateToLogin : AuthEffect()
}
