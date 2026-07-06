package com.cjthdev.ganamnesis.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cjthdev.ganamnesis.core.ui.theme.GanamnesisTheme
import com.cjthdev.ganamnesis.features.dashboard.ui.DashboardScreen

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    GanamnesisTheme {
        PreviewKoinScope(
            authRepository = FakeAuthRepository(initialUser = sampleUser),
            gameRepository = FakeGameRepository(initialLibrary = sampleGames),
        ) {
            DashboardScreen()
        }
    }
}
