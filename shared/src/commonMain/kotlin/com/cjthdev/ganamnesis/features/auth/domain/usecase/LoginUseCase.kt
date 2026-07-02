package com.cjthdev.ganamnesis.features.auth.domain.usecase

import com.cjthdev.ganamnesis.core.common.UseCase
import com.cjthdev.ganamnesis.core.model.User
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun login(params: LoginParams): Result<User> {
        return authRepository.login(params.email, params.password)
    }

    suspend fun loginWithGoogle(idToken: String): Result<User> {
        return authRepository.loginWithGoogle(idToken)
    }

    data class LoginParams(val email: String, val password: String)
}
