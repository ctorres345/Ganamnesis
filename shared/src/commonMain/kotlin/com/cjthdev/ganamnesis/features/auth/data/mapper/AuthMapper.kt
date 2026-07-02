package com.cjthdev.ganamnesis.features.auth.data.mapper

import com.cjthdev.ganamnesis.core.model.User
import dev.gitlive.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain() : User {
    return User(
        id = uid,
        username = displayName ?: "",
        email = email ?: "",
        avatarUrl = photoURL
    )
}