package com.cjthdev.ganamnesis.features.dashboard.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisAvatar
import com.cjthdev.ganamnesis.features.dashboard.presentation.viewmodel.DashboardIntent
import com.cjthdev.ganamnesis.features.dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.compose.koinInject

@Composable
fun DashboardScreen(
    onNavigateToLibrary: () -> Unit = {},
    onNavigateToBacklogChallenge: () -> Unit = {},
) {
    val viewModel: DashboardViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(DashboardIntent.Load)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GanamnesisAvatar(
                avatarUrl = state.user?.avatarUrl,
                fallbackText = state.user?.username ?: "?",
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = state.user?.username ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Level —",
                    fontSize = 14.sp,
                    color = Color.Gray,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        DashboardCard(
            title = "Current Backlog Challenge",
            onClick = onNavigateToBacklogChallenge,
        ) {
            Text("No active challenge yet", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        DashboardCard(
            title = "Badges",
            // TODO: navigate to Badges Screen (not built this pass)
            onClick = {},
            trailing = {
                TextButton(onClick = { /* TODO: See all badges (not built this pass) */ }) {
                    Text("See all")
                }
            },
        ) {
            Text("No badges yet", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        DashboardCard(
            title = "Game Library",
            onClick = onNavigateToLibrary,
        ) {
            Text("${state.libraryCount} games", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        DashboardCard(
            title = "Last Played Games",
            onClick = {},
        ) {
            Text("No games played yet", color = Color.Gray)
        }
    }
}

@Composable
private fun DashboardCard(
    title: String,
    onClick: () -> Unit,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                trailing?.invoke()
            }
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
