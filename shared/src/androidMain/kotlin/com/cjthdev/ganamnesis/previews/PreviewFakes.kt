package com.cjthdev.ganamnesis.previews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.cjthdev.ganamnesis.core.common.GoogleAuthHandler
import com.cjthdev.ganamnesis.core.common.GoogleSignInResult
import com.cjthdev.ganamnesis.core.data.repository.GameRepository
import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.model.SyncStatus
import com.cjthdev.ganamnesis.core.model.User
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import com.cjthdev.ganamnesis.features.auth.domain.usecase.GoogleSignInUseCase
import com.cjthdev.ganamnesis.features.auth.domain.usecase.LoginUseCase
import com.cjthdev.ganamnesis.features.auth.domain.usecase.SignUpUseCase
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthViewModel
import com.cjthdev.ganamnesis.features.dashboard.presentation.viewmodel.DashboardViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.KoinIsolatedContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

// Sample data — deliberately no cover/screenshot/avatar URLs so Coil's AsyncImage
// renders its deterministic placeholder instead of attempting a network fetch
// inside the preview sandbox.

val sampleUser =
    User(
        id = "preview-user-id",
        username = "wizardtester",
        email = "wizardtester@example.com",
        avatarUrl = null,
        hasCompletedSetup = true,
    )

val sampleGames =
    listOf(
        Game(id = "1", title = "Halo: Combat Evolved", releaseDate = "2001-11-15", rating = 4.5, platforms = listOf("Xbox")),
        Game(id = "2", title = "Half-Life 2", releaseDate = "2004-11-16", rating = 4.8, platforms = listOf("PC")),
        Game(id = "3", title = "Portal", releaseDate = "2007-10-10", rating = 4.9, platforms = listOf("PC")),
    )

val successSyncStatus = SyncStatus(isSyncing = false, progress = 1f, totalItems = 3, completedItems = 3)
val failedSyncStatus = SyncStatus(isSyncing = false, error = "Invalid Steam API key")

class FakeAuthRepository(
    initialUser: User? = sampleUser,
) : AuthRepository {
    private val userFlow = MutableStateFlow(initialUser)

    override fun getCurrentUser(): Flow<User?> = userFlow

    override suspend fun login(
        email: String,
        password: String,
    ): Result<User> = Result.success(userFlow.value ?: sampleUser)

    override suspend fun loginWithGoogle(idToken: String): Result<User> = Result.success(userFlow.value ?: sampleUser)

    override suspend fun signUp(
        email: String,
        password: String,
        username: String,
    ): Result<User> = Result.success(userFlow.value ?: sampleUser)

    override suspend fun logout() {
        userFlow.value = null
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        userFlow.value = user
        return Result.success(Unit)
    }
}

class FakeGameRepository(
    initialLibrary: List<Game> = emptyList(),
    private val searchResults: List<Game> = sampleGames,
    private val syncResult: SyncStatus = successSyncStatus,
) : GameRepository {
    private val libraryFlow = MutableStateFlow(initialLibrary)

    override suspend fun searchGames(query: String): Result<List<Game>> = Result.success(searchResults)

    override fun syncSteamLibrary(): Flow<SyncStatus> = flowOf(syncResult)

    override suspend fun addGame(game: Game): Result<Unit> {
        libraryFlow.value = libraryFlow.value + game
        return Result.success(Unit)
    }

    override fun getLibrary(): Flow<List<Game>> = libraryFlow
}

class FakeGoogleAuthHandler : GoogleAuthHandler {
    override suspend fun signIn(): GoogleSignInResult = GoogleSignInResult.Success("preview-fake-token")
}

private fun previewModule(
    authRepository: AuthRepository,
    gameRepository: GameRepository,
) = module {
    single { authRepository }
    single { gameRepository }
    single<GoogleAuthHandler> { FakeGoogleAuthHandler() }
    factoryOf(::LoginUseCase)
    factoryOf(::GoogleSignInUseCase)
    factoryOf(::SignUpUseCase)
    factoryOf(::AuthViewModel)
    factoryOf(::DashboardViewModel)
}

@Composable
fun PreviewKoinScope(
    authRepository: AuthRepository = FakeAuthRepository(),
    gameRepository: GameRepository = FakeGameRepository(),
    content: @Composable () -> Unit,
) {
    val app = remember { koinApplication { modules(previewModule(authRepository, gameRepository)) } }
    KoinIsolatedContext(context = app) { content() }
}
