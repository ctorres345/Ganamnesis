package com.cjthdev.ganamnesis.features.auth.domain.usecase

import com.cjthdev.ganamnesis.core.common.UseCase
import com.cjthdev.ganamnesis.core.model.User
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val authRepository: AuthRepository,
) : UseCase<LoginUseCase.Params, Result<User>> {
    override fun invoke(params: Params): Flow<Result<User>> =
        flow {
            emit(authRepository.login(params.email, params.password))
        }

    data class Params(
        val email: String,
        val password: String,
    )
}
