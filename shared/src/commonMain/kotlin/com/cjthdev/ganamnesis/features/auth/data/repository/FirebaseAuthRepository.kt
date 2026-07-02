package com.cjthdev.ganamnesis.features.auth.data.repository

import com.cjthdev.ganamnesis.core.model.User
import com.cjthdev.ganamnesis.features.auth.data.mapper.toDomain
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.*

class FirebaseAuthRepository : AuthRepository {
    private val auth = Firebase.auth
    
    // Local cache to support custom fields like steamKey/rawgKey
    private val _userCache = MutableStateFlow<User?>(null)

    override fun getCurrentUser(): Flow<User?> {
        return auth.authStateChanged.map { firebaseUser ->
            val domainUser = firebaseUser?.toDomain()
            if (domainUser != null) {
                // Merge with cache if IDs match to preserve keys
                val cached = _userCache.value
                if (cached?.id == domainUser.id) {
                    cached
                } else {
                    _userCache.value = domainUser
                    domainUser
                }
            } else {
                _userCache.value = null
                null
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            val user = result.user?.toDomain() ?: throw Exception("User not found after login")
            _userCache.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.credential(idToken = idToken, accessToken = null)
            val result = auth.signInWithCredential(credential)
            val user = result.user?.toDomain() ?: throw Exception("Google login failed")
            _userCache.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
        _userCache.value = null
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        _userCache.value = user
        return Result.success(Unit)
    }
}
