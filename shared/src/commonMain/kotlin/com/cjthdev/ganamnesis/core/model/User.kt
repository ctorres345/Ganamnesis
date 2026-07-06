package com.cjthdev.ganamnesis.core.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val steamKey: String? = null,
    val steamId: String? = null,
    val retroUser: String? = null,
    val retroApiKey: String? = null,
    val hasCompletedSetup: Boolean = false,
)
