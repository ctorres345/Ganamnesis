package com.cjthdev.ganamnesis.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val steamKey: String? = null,
    val steamId: String? = null,
    val retroUser: String? = null,
    val retroApiKey: String? = null,
    val hasCompletedSetup: Boolean = false,
)
