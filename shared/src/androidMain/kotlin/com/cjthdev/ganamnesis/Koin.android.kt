package com.cjthdev.ganamnesis

import com.cjthdev.ganamnesis.core.common.AndroidGoogleAuthHandler
import com.cjthdev.ganamnesis.core.common.GoogleAuthHandler
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<GoogleAuthHandler> {
        AndroidGoogleAuthHandler(
            context = get(),
            webClientId = "255010764127-u0s0t4gqn1ebakgm59ule3cqph4m9pge.apps.googleusercontent.com"
        )
    }
}