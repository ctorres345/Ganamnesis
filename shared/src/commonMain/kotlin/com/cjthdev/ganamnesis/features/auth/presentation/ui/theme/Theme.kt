package com.cjthdev.ganamnesis.features.auth.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun GanamnesisTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        primary = PrimaryDark,
        background = BackgroundLight,
        surface = BackgroundLight,
        onBackground = Color.Black,
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}