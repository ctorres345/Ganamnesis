package com.cjthdev.ganamnesis.core.data.repository

import com.cjthdev.ganamnesis.core.data.mapper.toDomain
import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.model.SyncStatus
import com.cjthdev.ganamnesis.core.network.RawgService
import com.cjthdev.ganamnesis.core.network.SteamService
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class DefaultGameRepository(
    private val authRepository: AuthRepository,
    private val rawgService: RawgService,
    private val steamService: SteamService
) : GameRepository {

    private val _library = MutableStateFlow<List<Game>>(emptyList())

    override suspend fun searchGames(query: String): Result<List<Game>> {
        val user = authRepository.getCurrentUser().first() 
            ?: return Result.failure(Exception("No user logged in"))

        return try {
            if (!user.steamKey.isNullOrBlank() && !user.steamId.isNullOrBlank()) {
                if (!user.rawgKey.isNullOrBlank()) {
                     Result.success(rawgService.searchGames(query, user.rawgKey).map { it.toDomain() })
                } else {
                     Result.failure(Exception("RAWG Key missing for search"))
                }
            } else if (!user.rawgKey.isNullOrBlank()) {
                Result.success(rawgService.searchGames(query, user.rawgKey).map { it.toDomain() })
            } else {
                Result.failure(Exception("No data source established (Steam or RAWG)"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun syncSteamLibrary(): Flow<SyncStatus> = flow {
        val user = authRepository.getCurrentUser().first()
        if (user?.steamKey == null || user.steamId == null) {
            emit(SyncStatus(error = "Steam credentials missing"))
            return@flow
        }

        emit(SyncStatus(isSyncing = true, progress = 0.1f))
        
        try {
            val steamGames = steamService.getOwnedGames(user.steamKey, user.steamId)
            val total = steamGames.size
            
            val domainGames = mutableListOf<Game>()
            steamGames.forEachIndexed { index, steamGame ->
                val game = steamGame.toDomain()
                domainGames.add(game)
                
                emit(SyncStatus(
                    isSyncing = true,
                    progress = (index + 1).toFloat() / total,
                    currentItem = game.title,
                    totalItems = total,
                    completedItems = index + 1
                ))
                delay(50) // Simulate processing time
            }
            
            _library.value = domainGames
            emit(SyncStatus(isSyncing = false, progress = 1f, totalItems = total, completedItems = total))
        } catch (e: Exception) {
            emit(SyncStatus(error = e.message ?: "Unknown sync error"))
        }
    }

    override suspend fun addGame(game: Game): Result<Unit> {
        _library.value += game
        return Result.success(Unit)
    }

    override fun getLibrary(): Flow<List<Game>> = _library.asStateFlow()
}
