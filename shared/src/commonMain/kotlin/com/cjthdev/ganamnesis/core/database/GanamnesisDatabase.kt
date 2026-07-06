package com.cjthdev.ganamnesis.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.cjthdev.ganamnesis.core.database.converter.ListStringConverter
import com.cjthdev.ganamnesis.core.database.dao.GameDao
import com.cjthdev.ganamnesis.core.database.dao.UserDao
import com.cjthdev.ganamnesis.core.database.entity.GameEntity
import com.cjthdev.ganamnesis.core.database.entity.UserEntity

@Database(entities = [GameEntity::class, UserEntity::class], version = 2, exportSchema = true)
@TypeConverters(ListStringConverter::class)
@ConstructedBy(GanamnesisDatabaseConstructor::class)
abstract class GanamnesisDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    abstract fun userDao(): UserDao
}

// Room's KSP compiler generates the platform `actual` for this.
expect object GanamnesisDatabaseConstructor : RoomDatabaseConstructor<GanamnesisDatabase>
