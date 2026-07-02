package com.cjthdev.ganamnesis.features.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cjthdev.ganamnesis.core.common.BaseViewModel
import com.cjthdev.ganamnesis.core.common.GoogleAuthHandler
import com.cjthdev.ganamnesis.core.common.GoogleSignInResult
import com.cjthdev.ganamnesis.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val googleAuthHandler: GoogleAuthHandler
) : BaseViewModel<AuthState, AuthIntent, AuthEffect>() {

    override fun createInitialState(): AuthState = AuthState()

    override fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Login -> performLogin(intent.email, intent.password)
            is AuthIntent.SignUp -> performSignUp(intent.email, intent.password)
            is AuthIntent.SendPasswordReset -> performPasswordReset(intent.email)
            AuthIntent.LoginWithGoogle -> performGoogleLogin()
            AuthIntent.Logout -> { /* Handle logout */ }
        }
    }

    private fun performLogin(email: String, password: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            val result = loginUseCase.login(LoginUseCase.LoginParams(email, password))
            result.onSuccess { user ->
                setState { copy(isLoading = false, user = user) }
                setEffect { AuthEffect.NavigateToHome }
            }.onFailure { exception ->
                setState { copy(isLoading = false, error = exception.message) }
                setEffect { AuthEffect.ShowError(exception.message ?: "Unknown error") }
            }
        }
    }

    private fun performSignUp(email: String, password: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            // For now, let's just simulate or add a SignUp method to LoginUseCase later
            setState { copy(isLoading = false, error = "SignUp not yet implemented") }
        }
    }

    private fun performPasswordReset(email: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            // Simulate success
            setState { copy(isLoading = false, isPasswordResetSent = true) }
        }
    }

    private fun performGoogleLogin() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            when (val result = googleAuthHandler.signIn()) {
                is GoogleSignInResult.Success -> {
                    val loginResult = loginUseCase.loginWithGoogle(result.idToken)
                    loginResult.onSuccess { user ->
                        setState { copy(isLoading = false, user = user) }
                        setEffect { AuthEffect.NavigateToHome }
                    }.onFailure { exception ->
                        setState { copy(isLoading = false, error = exception.message) }
                        setEffect { AuthEffect.ShowError(exception.message ?: "Unknown error") }
                    }
                }
                is GoogleSignInResult.Cancelled -> {
                    setState { copy(isLoading = false) }
                }
                is GoogleSignInResult.Failure -> {
                    setState { copy(isLoading = false, error = result.message) }
                    setEffect { AuthEffect.ShowError(result.message) }
                }
            }
        }
    }
}
