package com.cjthdev.ganamnesis

import com.cjthdev.ganamnesis.core.data.repository.DefaultGameRepository
import com.cjthdev.ganamnesis.core.data.repository.GameRepository
import com.cjthdev.ganamnesis.core.network.RawgService
import com.cjthdev.ganamnesis.core.network.RetroService
import com.cjthdev.ganamnesis.core.network.SteamService
import com.cjthdev.ganamnesis.core.network.createHttpClient
import com.cjthdev.ganamnesis.features.auth.data.repository.FirebaseAuthRepository
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import com.cjthdev.ganamnesis.features.auth.domain.usecase.LoginUseCase
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthViewModel
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
    // Network
    single { createHttpClient() }
    single { RawgService(get()) }
    single { SteamService(get()) }
    single { RetroService(get()) }

    // Repositories
    single<AuthRepository> { FirebaseAuthRepository() }
    single<GameRepository> { DefaultGameRepository(get(), get(), get()) }

    // Auth Feature
    factory { LoginUseCase(get()) }
    factory { AuthViewModel(get(), get()) }
    
    // Setup Feature
    factory { SetupViewModel(get(), get()) }
}

expect val platformModule : Module
