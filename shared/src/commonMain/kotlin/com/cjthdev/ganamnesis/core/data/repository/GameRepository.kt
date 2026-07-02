package com.cjthdev.ganamnesis.core.data.repository

import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.model.SyncStatus
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun searchGames(query: String): Result<List<Game>>
    fun syncSteamLibrary(): Flow<SyncStatus>
    suspend fun addGame(game: Game): Result<Unit>
    fun getLibrary(): Flow<List<Game>>
}
