package com.cjthdev.ganamnesis.core.common

import kotlinx.coroutines.flow.Flow

/**
 * Base UseCase for business logic
 */
interface UseCase<in P, out R> {
    operator fun invoke(params: P): Flow<R>
}

/**
 * Base UseCase for business logic without parameters
 */
interface NoParamsUseCase<out R> {
    operator fun invoke(): Flow<R>
}
