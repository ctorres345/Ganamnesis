package com.cjthdev.ganamnesis.features.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.features.settings.presentation.viewmodel.SettingsEffect
import com.cjthdev.ganamnesis.features.settings.presentation.viewmodel.SettingsIntent
import com.cjthdev.ganamnesis.features.settings.presentation.viewmodel.SettingsViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(
    onNavigateToSteamSync: () -> Unit,
    onLoggedOut: () -> Unit,
) {
    val viewModel: SettingsViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
    ) {
        Text(text = "Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        SettingsRow(text = "Steam Sync", onClick = onNavigateToSteamSync)

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoggingOut) {
            CircularProgressIndicator()
        } else {
            SettingsRow(
                text = "Log Off",
                onClick = { viewModel.handleIntent(SettingsIntent.Logout) },
                textColor = MaterialTheme.colorScheme.error,
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsEffect.NavigateToLogin -> onLoggedOut()
            }
        }
    }
}

@Composable
private fun SettingsRow(
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.Black,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text, color = textColor, fontWeight = FontWeight.Bold)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = textColor)
        }
    }
}
