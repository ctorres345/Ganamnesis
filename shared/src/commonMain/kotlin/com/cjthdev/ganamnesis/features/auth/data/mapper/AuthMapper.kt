package com.cjthdev.ganamnesis.features.auth.data.mapper

import com.cjthdev.ganamnesis.core.database.entity.UserEntity
import com.cjthdev.ganamnesis.core.model.User
import dev.gitlive.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): User =
    User(
        id = uid,
        username = displayName ?: "",
        email = email ?: "",
        avatarUrl = photoURL,
    )

fun UserEntity.toDomain(): User =
    User(
        id = id,
        username = username,
        email = email,
        avatarUrl = avatarUrl,
        steamKey = steamKey,
        steamId = steamId,
        retroUser = retroUser,
        retroApiKey = retroApiKey,
        hasCompletedSetup = hasCompletedSetup,
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = id,
        username = username,
        email = email,
        avatarUrl = avatarUrl,
        steamKey = steamKey,
        steamId = steamId,
        retroUser = retroUser,
        retroApiKey = retroApiKey,
        hasCompletedSetup = hasCompletedSetup,
    )
