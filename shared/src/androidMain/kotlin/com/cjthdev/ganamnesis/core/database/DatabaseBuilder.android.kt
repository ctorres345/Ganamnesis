package com.cjthdev.ganamnesis.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Plain (non-expect/actual) Android-only builder — Room's KMP builder API needs a
 * platform Context, which has no commonMain equivalent to declare an `expect fun`
 * against. When iOS lands, that's when this becomes a real expect/actual pair
 * (each platform capturing its own storage path internally, zero-arg signature).
 */
fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<GanamnesisDatabase> {
    val dbFile = context.applicationContext.getDatabasePath("ganamnesis.db")
    return Room.databaseBuilder<GanamnesisDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath,
    )
}
