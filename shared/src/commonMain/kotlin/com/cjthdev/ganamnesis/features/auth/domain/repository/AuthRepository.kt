package com.cjthdev.ganamnesis.features.auth.domain.repository

import com.cjthdev.ganamnesis.core.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>

    suspend fun login(
        email: String,
        password: String,
    ): Result<User>

    suspend fun loginWithGoogle(idToken: String): Result<User>

    suspend fun signUp(
        email: String,
        password: String,
        username: String,
    ): Result<User>

    suspend fun logout()

    suspend fun updateUser(user: User): Result<Unit>
}
