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
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisTextField
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupIntent
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import org.koin.compose.koinInject

@Composable
fun SearchGameScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val viewModel: SetupViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()
    
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Add a Game",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Look for a game by name",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        GanamnesisTextField(
            value = query,
            onValueChange = { 
                query = it
                if (it.length > 2) {
                    viewModel.handleIntent(SetupIntent.SearchGames(it))
                }
            },
            label = "Search",
            placeholder = "Halo..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(state.error!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(state.searchResults) { game ->
                        ListItem(
                            headlineContent = { Text(game.title) },
                            supportingContent = { Text(game.releaseDate ?: "") },
                            trailingContent = {
                                TextButton(onClick = { viewModel.handleIntent(SetupIntent.AddGame(game)) }) {
                                    Text("Add")
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GanamnesisButton(
            text = "Continue",
            onClick = onNext
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("Back", color = Color.Gray)
        }
    }
}
