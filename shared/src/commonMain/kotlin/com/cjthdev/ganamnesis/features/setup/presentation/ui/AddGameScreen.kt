package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.cjthdev.ganamnesis.core.model.Game
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisTextField
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupIntent
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel

@Composable
fun AddGameScreen(
    viewModel: SetupViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    var query by remember { mutableStateOf("") }
    var selectedGame by remember { mutableStateOf<Game?>(null) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Add a Game",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "Starting adding games to fill your library",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp),
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
            label = "Name...",
            placeholder = "Halo...",
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                LazyColumn {
                    items(state.searchResults) { game ->
                        ListItem(
                            modifier = Modifier.clickable { selectedGame = game },
                            headlineContent = { Text(game.title) },
                            supportingContent = { Text(game.releaseDate ?: "") },
                            leadingContent = {
                                AsyncImage(
                                    model = game.coverUrl,
                                    contentDescription = game.title,
                                    modifier = Modifier.size(48.dp),
                                )
                            },
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("Back", color = Color.Gray)
        }
    }

    selectedGame?.let { game ->
        Dialog(onDismissRequest = { selectedGame = null }) {
            Surface(shape = RoundedCornerShape(24.dp), color = Color.White) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        AsyncImage(
                            model = game.coverUrl,
                            contentDescription = game.title,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = game.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                        if (game.rating != null) {
                            Text(
                                text = "Rating: ${game.rating}",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }

                        if (game.platforms.isNotEmpty()) {
                            Text(
                                text = game.platforms.joinToString(", "),
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }

                        if (game.screenshots.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            LazyRow {
                                items(game.screenshots) { screenshot ->
                                    AsyncImage(
                                        model = screenshot,
                                        contentDescription = null,
                                        modifier =
                                            Modifier
                                                .padding(end = 8.dp)
                                                .size(80.dp)
                                                .clip(RoundedCornerShape(8.dp)),
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        GanamnesisButton(
                            text = "Add",
                            onClick = {
                                viewModel.handleIntent(SetupIntent.AddGame(game))
                                selectedGame = null
                                onDone()
                            },
                        )
                    }

                    IconButton(
                        onClick = { selectedGame = null },
                        modifier = Modifier.align(Alignment.TopEnd),
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }
        }
    }
}
