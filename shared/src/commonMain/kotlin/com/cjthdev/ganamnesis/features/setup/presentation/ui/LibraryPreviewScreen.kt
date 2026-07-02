package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupIntent
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import org.koin.compose.koinInject

@Composable
fun LibraryPreviewScreen(
    onFinish: () -> Unit
) {
    val viewModel: SetupViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "My Library",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Here are the games in your collection",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.addedGames) { game ->
                ListItem(
                    headlineContent = { Text(game.title) },
                    supportingContent = { Text(if (game.steamId != null) "Steam" else "RAWG") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GanamnesisButton(
            text = "Go to my dashboard",
            onClick = {
                viewModel.handleIntent(SetupIntent.CompleteSetup)
                onFinish()
            }
        )
    }
}
