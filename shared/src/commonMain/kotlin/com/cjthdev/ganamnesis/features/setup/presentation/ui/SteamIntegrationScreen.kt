package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisTextField
import com.cjthdev.ganamnesis.core.ui.components.WizardProgressIndicator
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupIntent
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel

private const val STEAM_API_KEY_GUIDE_URL = "https://steamcommunity.com/dev/apikey"

private enum class SteamConnectState { IDLE, LOADING, SUCCESS, FAILED }

@Composable
fun SteamIntegrationScreen(
    viewModel: SetupViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    showProgress: Boolean = true,
) {
    val state by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    var key by remember { mutableStateOf("") }
    var steamId by remember { mutableStateOf("") }
    var prefilled by remember { mutableStateOf(false) }

    val connectState =
        when {
            state.syncStatus?.isSyncing == true -> SteamConnectState.LOADING
            state.steamSyncAttempted && state.syncStatus?.error != null -> SteamConnectState.FAILED
            state.steamSyncAttempted && state.syncStatus?.error == null -> SteamConnectState.SUCCESS
            else -> SteamConnectState.IDLE
        }

    LaunchedEffect(connectState) {
        if (connectState == SteamConnectState.SUCCESS) {
            onSuccess()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(SetupIntent.LoadSteamCredentials)
    }

    LaunchedEffect(state.steamCredentialsLoaded) {
        if (state.steamCredentialsLoaded && !prefilled) {
            key = state.steamKey
            steamId = state.steamId
            prefilled = true
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        if (showProgress) {
            WizardProgressIndicator(currentStep = 1, totalSteps = 3)
            Spacer(modifier = Modifier.height(24.dp))
        }

        Text(
            text = if (state.hasExistingSteamCredentials) "Update your Steam Key" else "Steam Integration",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "For this integration we require your steam API KEY.",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        GanamnesisTextField(
            value = key,
            onValueChange = { key = it },
            label = "Steam API KEY",
            placeholder = "My Key...",
        )

        Spacer(modifier = Modifier.height(8.dp))

        val annotatedGuideText =
            buildAnnotatedString {
                append("If you don't know how to get the API KEY, you can follow ")
                pushStringAnnotation(tag = "URL", annotation = STEAM_API_KEY_GUIDE_URL)
                withStyle(SpanStyle(color = Color(0xFFF4511E), fontWeight = FontWeight.Bold)) {
                    append("this guide")
                }
                pop()
                append(".")
            }
        ClickableText(
            text = annotatedGuideText,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            onClick = { offset ->
                annotatedGuideText
                    .getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()
                    ?.let { uriHandler.openUri(it.item) }
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        GanamnesisTextField(
            value = steamId,
            onValueChange = { steamId = it },
            label = "Steam ID",
            placeholder = "Your numeric Steam ID...",
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (connectState == SteamConnectState.LOADING) {
            CircularProgressIndicator()
        } else {
            GanamnesisButton(
                text =
                    when {
                        connectState == SteamConnectState.FAILED -> "Try Again"
                        state.hasExistingSteamCredentials -> "Update"
                        else -> "Continue"
                    },
                onClick = {
                    viewModel.handleIntent(SetupIntent.UpdateSteamKeys(key, steamId))
                    viewModel.handleIntent(SetupIntent.StartSteamSync)
                },
            )
        }

        if (connectState == SteamConnectState.FAILED) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.syncStatus?.error ?: "Connection failed. Please check your key and try again.",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("Back", color = Color.Gray)
        }
    }
}
