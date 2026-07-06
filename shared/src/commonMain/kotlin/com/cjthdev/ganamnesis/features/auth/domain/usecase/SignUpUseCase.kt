package com.cjthdev.ganamnesis.features.auth.domain.usecase

import com.cjthdev.ganamnesis.core.common.UseCase
import com.cjthdev.ganamnesis.core.model.User
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpUseCase(
    private val authRepository: AuthRepository,
) : UseCase<SignUpUseCase.Params, Result<User>> {
    override fun invoke(params: Params): Flow<Result<User>> =
        flow {
            emit(authRepository.signUp(params.email, params.password, params.username))
        }

    data class Params(
        val email: String,
        val password: String,
        val username: String,
    )
}
