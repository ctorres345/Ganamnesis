package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisTextField
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupIntent
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import org.koin.compose.koinInject

@Composable
fun SteamSetupScreen(
    onBack: () -> Unit,
    onSyncStarted: () -> Unit
) {
    val viewModel: SetupViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    var key by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Sync your Steam Library",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "This will offer a personalized experience",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        GanamnesisTextField(
            value = key,
            onValueChange = { key = it },
            label = "Steam Web Api Key",
            placeholder = "My Key..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        GanamnesisTextField(
            value = id,
            onValueChange = { id = it },
            label = "Steam Id",
            placeholder = "My Id..."
        )

        Spacer(modifier = Modifier.height(32.dp))

        GanamnesisButton(
            text = "Continue",
            onClick = {
                viewModel.handleIntent(SetupIntent.UpdateSteamKeys(key, id))
                viewModel.handleIntent(SetupIntent.StartSteamSync)
                onSyncStarted()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("Back", color = Color.Gray)
        }
    }
}
