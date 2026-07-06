package com.cjthdev.ganamnesis

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.cjthdev.ganamnesis.core.common.AndroidGoogleAuthHandler
import com.cjthdev.ganamnesis.core.common.GoogleAuthHandler
import com.cjthdev.ganamnesis.core.database.GanamnesisDatabase
import com.cjthdev.ganamnesis.core.database.getDatabaseBuilder
import com.cjthdev.ganamnesis.core.network.IgdbAuthTokenProvider
import com.cjthdev.ganamnesis.shared.BuildConfig
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single<GoogleAuthHandler> {
            AndroidGoogleAuthHandler(
                context = get(),
                webClientId = "255010764127-u0s0t4gqn1ebakgm59ule3cqph4m9pge.apps.googleusercontent.com",
            )
        }
        single {
            IgdbAuthTokenProvider(
                clientId = BuildConfig.IGDB_CLIENT_ID,
                clientSecret = BuildConfig.IGDB_CLIENT_SECRET,
                client = get(),
            )
        }
        single {
            getDatabaseBuilder(get<Context>())
                .addMigrations(
                    Migration(1, 2) { db ->
                        db.execSQL("ALTER TABLE users ADD COLUMN hasCompletedSetup INTEGER NOT NULL DEFAULT 0")
                    },
                ).setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
        single { get<GanamnesisDatabase>().gameDao() }
        single { get<GanamnesisDatabase>().userDao() }
    }
