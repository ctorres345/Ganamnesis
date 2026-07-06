package com.cjthdev.ganamnesis

import com.cjthdev.ganamnesis.core.data.repository.DefaultGameRepository
import com.cjthdev.ganamnesis.core.data.repository.GameRepository
import com.cjthdev.ganamnesis.core.network.IgdbService
import com.cjthdev.ganamnesis.core.network.RetroService
import com.cjthdev.ganamnesis.core.network.SteamService
import com.cjthdev.ganamnesis.core.network.createHttpClient
import com.cjthdev.ganamnesis.features.auth.data.repository.FirebaseAuthRepository
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import com.cjthdev.ganamnesis.features.auth.domain.usecase.GoogleSignInUseCase
import com.cjthdev.ganamnesis.features.auth.domain.usecase.LoginUseCase
import com.cjthdev.ganamnesis.features.auth.domain.usecase.SignUpUseCase
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthViewModel
import com.cjthdev.ganamnesis.features.dashboard.presentation.viewmodel.DashboardViewModel
import com.cjthdev.ganamnesis.features.library.presentation.viewmodel.LibraryViewModel
import com.cjthdev.ganamnesis.features.profile.presentation.viewmodel.ProfileViewModel
import com.cjthdev.ganamnesis.features.settings.presentation.viewmodel.SettingsViewModel
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

// GanamnesisDatabase/GameDao/UserDao are bound in platformModule instead of here: Room's
// builder needs a platform Context, which only androidMain's platformModule can supply
// (mirrors the existing GoogleAuthHandler seam).

val networkModule =
    module {
        single { createHttpClient() }
        single { IgdbService(get(), get()) }
        single { SteamService(get()) }
        single { RetroService(get()) }
    }

val dataModule =
    module {
        single<AuthRepository> { FirebaseAuthRepository(get()) }
        single<GameRepository> { DefaultGameRepository(get(), get(), get(), get()) }
    }

val authFeatureModule =
    module {
        factoryOf(::LoginUseCase)
        factoryOf(::GoogleSignInUseCase)
        factoryOf(::SignUpUseCase)
        factoryOf(::AuthViewModel)
    }

val setupFeatureModule =
    module {
        factoryOf(::SetupViewModel)
    }

val dashboardFeatureModule =
    module {
        factoryOf(::DashboardViewModel)
    }

val mainFeatureModule =
    module {
        factoryOf(::ProfileViewModel)
        factoryOf(::LibraryViewModel)
        factoryOf(::SettingsViewModel)
    }

val sharedModules =
    listOf(
        networkModule,
        dataModule,
        authFeatureModule,
        setupFeatureModule,
        dashboardFeatureModule,
        mainFeatureModule,
    )

expect val platformModule: Module

fun initKoin(config: (KoinApplication.() -> Unit)? = null) =
    startKoin {
        config?.invoke(this)
        modules(sharedModules + platformModule)
    }
