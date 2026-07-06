package com.cjthdev.ganamnesis.features.library.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.cjthdev.ganamnesis.features.library.presentation.viewmodel.LibraryIntent
import com.cjthdev.ganamnesis.features.library.presentation.viewmodel.LibraryViewModel
import org.koin.compose.koinInject

@Composable
fun LibraryScreen() {
    val viewModel: LibraryViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(LibraryIntent.Load)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
    ) {
        Text(text = "Library", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.games.isEmpty()) {
                Text(
                    text = "No games in your library yet",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                LazyColumn {
                    items(state.games) { game ->
                        ListItem(
                            headlineContent = { Text(game.title) },
                            supportingContent = { Text(if (game.steamId != null) "Steam" else "IGDB") },
                            leadingContent = {
                                AsyncImage(
                                    model = game.coverUrl,
                                    contentDescription = game.title,
                                    modifier =
                                        Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}
