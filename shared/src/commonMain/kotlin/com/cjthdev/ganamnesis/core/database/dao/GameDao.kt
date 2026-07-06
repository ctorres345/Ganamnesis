package com.cjthdev.ganamnesis.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.cjthdev.ganamnesis.core.database.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM games")
    fun observeAll(): Flow<List<GameEntity>>

    @Upsert
    suspend fun upsert(game: GameEntity)
}
