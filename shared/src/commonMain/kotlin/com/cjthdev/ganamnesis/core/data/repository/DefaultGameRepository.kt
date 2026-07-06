package com.cjthdev.ganamnesis.core.data.repository

import com.cjthdev.ganamnesis.core.data.mapper.toDomain
import com.cjthdev.ganamnesis.core.data.mapper.toEntity
import com.cjthdev.ganamnesis.core.database.dao.GameDao
import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.model.SyncStatus
import com.cjthdev.ganamnesis.core.network.IgdbService
import com.cjthdev.ganamnesis.core.network.SteamService
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class DefaultGameRepository(
    private val authRepository: AuthRepository,
    private val igdbService: IgdbService,
    private val steamService: SteamService,
    private val gameDao: GameDao,
) : GameRepository {
    override suspend fun searchGames(query: String): Result<List<Game>> =
        try {
            Result.success(igdbService.searchGames(query).map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun syncSteamLibrary(): Flow<SyncStatus> =
        flow {
            val user = authRepository.getCurrentUser().first()
            if (user?.steamKey == null || user.steamId == null) {
                emit(SyncStatus(error = "Steam credentials missing"))
                return@flow
            }

            emit(SyncStatus(isSyncing = true, progress = 0.1f))

            try {
                val steamGames = steamService.getOwnedGames(user.steamKey, user.steamId)
                val total = steamGames.size

                steamGames.forEachIndexed { index, steamGame ->
                    val game = steamGame.toDomain()
                    gameDao.upsert(game.toEntity())

                    emit(
                        SyncStatus(
                            isSyncing = true,
                            progress = (index + 1).toFloat() / total,
                            currentItem = game.title,
                            totalItems = total,
                            completedItems = index + 1,
                        ),
                    )
                    delay(50) // Simulate processing time
                }

                emit(SyncStatus(isSyncing = false, progress = 1f, totalItems = total, completedItems = total))
            } catch (e: Exception) {
                emit(SyncStatus(error = e.message ?: "Unknown sync error"))
            }
        }

    override suspend fun addGame(game: Game): Result<Unit> =
        try {
            gameDao.upsert(game.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun getLibrary(): Flow<List<Game>> = gameDao.observeAll().map { entities -> entities.map { it.toDomain() } }
}
