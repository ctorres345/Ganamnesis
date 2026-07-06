package com.cjthdev.ganamnesis.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun GanamnesisTheme(content: @Composable () -> Unit) {
    val colorScheme =
        lightColorScheme(
            primary = PrimaryDark,
            onPrimary = Color.White,
            background = BackgroundLight,
            surface = SurfaceWhite,
            onSurface = Color.Black,
            onBackground = Color.Black,
            error = ErrorRed,
            outline = GrayText,
        )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
