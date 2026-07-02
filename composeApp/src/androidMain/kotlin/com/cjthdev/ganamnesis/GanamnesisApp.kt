package com.cjthdev.ganamnesis

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GanamnesisApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GanamnesisApp)
            modules(sharedModule, platformModule)
        }
    }
}
