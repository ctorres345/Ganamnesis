package com.cjthdev.ganamnesis.features.auth.domain.usecase

import com.cjthdev.ganamnesis.core.common.UseCase
import com.cjthdev.ganamnesis.core.model.User
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GoogleSignInUseCase(
    private val authRepository: AuthRepository,
) : UseCase<String, Result<User>> {
    override fun invoke(params: String): Flow<Result<User>> =
        flow {
            emit(authRepository.loginWithGoogle(params))
        }
}
