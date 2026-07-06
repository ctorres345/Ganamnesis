package com.cjthdev.ganamnesis.previews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.cjthdev.ganamnesis.core.ui.theme.GanamnesisTheme
import com.cjthdev.ganamnesis.features.setup.presentation.ui.AddGameScreen
import com.cjthdev.ganamnesis.features.setup.presentation.ui.SetupWizard1Screen
import com.cjthdev.ganamnesis.features.setup.presentation.ui.SetupWizard2Screen
import com.cjthdev.ganamnesis.features.setup.presentation.ui.SetupWizard3Screen
import com.cjthdev.ganamnesis.features.setup.presentation.ui.SteamIntegrationScreen
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupIntent
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel

@Preview(showBackground = true)
@Composable
fun SetupWizard1ScreenPreview() {
    GanamnesisTheme {
        SetupWizard1Screen(onSync = {}, onSkip = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SetupWizard2ScreenPreview() {
    GanamnesisTheme {
        SetupWizard2Screen(onAddGame = {}, onSkip = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SetupWizard3ScreenPreview() {
    GanamnesisTheme {
        val viewModel = remember { SetupViewModel(FakeAuthRepository(), FakeGameRepository()) }
        SetupWizard3Screen(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun SteamIntegrationScreenPreview() {
    GanamnesisTheme {
        val viewModel = remember { SetupViewModel(FakeAuthRepository(), FakeGameRepository()) }
        SteamIntegrationScreen(viewModel = viewModel, onBack = {}, onSuccess = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SteamIntegrationScreenFailedPreview() {
    GanamnesisTheme {
        val viewModel =
            remember {
                SetupViewModel(FakeAuthRepository(), FakeGameRepository(syncResult = failedSyncStatus)).apply {
                    handleIntent(SetupIntent.UpdateSteamKeys("bad-key", "123456"))
                    handleIntent(SetupIntent.StartSteamSync)
                }
            }
        SteamIntegrationScreen(viewModel = viewModel, onBack = {}, onSuccess = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AddGameScreenPreview() {
    GanamnesisTheme {
        val viewModel = remember { SetupViewModel(FakeAuthRepository(), FakeGameRepository()) }
        AddGameScreen(viewModel = viewModel, onBack = {}, onDone = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AddGameScreenPopulatedPreview() {
    GanamnesisTheme {
        val viewModel =
            remember {
                SetupViewModel(FakeAuthRepository(), FakeGameRepository(searchResults = sampleGames)).apply {
                    handleIntent(SetupIntent.SearchGames("halo"))
                }
            }
        AddGameScreen(viewModel = viewModel, onBack = {}, onDone = {})
    }
}
