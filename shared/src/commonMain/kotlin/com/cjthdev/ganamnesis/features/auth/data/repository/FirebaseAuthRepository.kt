package com.cjthdev.ganamnesis.features.auth.data.repository

import com.cjthdev.ganamnesis.core.database.dao.UserDao
import com.cjthdev.ganamnesis.core.model.User
import com.cjthdev.ganamnesis.features.auth.data.mapper.toDomain
import com.cjthdev.ganamnesis.features.auth.data.mapper.toEntity
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class FirebaseAuthRepository(
    private val userDao: UserDao,
) : AuthRepository {
    private val auth = Firebase.auth

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCurrentUser(): Flow<User?> =
        auth.authStateChanged.flatMapLatest { firebaseUser ->
            if (firebaseUser == null) {
                flowOf(null)
            } else {
                userDao.observeById(firebaseUser.uid).map { cached ->
                    cached?.toDomain() ?: firebaseUser.toDomain()
                }
            }
        }

    override suspend fun login(
        email: String,
        password: String,
    ): Result<User> =
        try {
            val result = auth.signInWithEmailAndPassword(email, password)
            val user = result.user?.toDomain() ?: throw Exception("User not found after login")
            cacheIfAbsent(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun loginWithGoogle(idToken: String): Result<User> =
        try {
            val credential = GoogleAuthProvider.credential(idToken = idToken, accessToken = null)
            val result = auth.signInWithCredential(credential)
            val user = result.user?.toDomain() ?: throw Exception("Google login failed")
            cacheIfAbsent(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun signUp(
        email: String,
        password: String,
        username: String,
    ): Result<User> =
        try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            val createdUser = result.user ?: throw Exception("Sign up failed")
            createdUser.updateProfile(displayName = username)
            val user =
                User(
                    id = createdUser.uid,
                    username = username,
                    email = email,
                    avatarUrl = createdUser.photoURL,
                )
            userDao.upsert(user.toEntity())
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun updateUser(user: User): Result<Unit> =
        try {
            userDao.upsert(user.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    private suspend fun cacheIfAbsent(user: User) {
        if (userDao.observeById(user.id).first() == null) {
            userDao.upsert(user.toEntity())
        }
    }
}
