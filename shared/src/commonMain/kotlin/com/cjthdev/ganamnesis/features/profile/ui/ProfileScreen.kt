package com.cjthdev.ganamnesis.features.profile.ui

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
import com.cjthdev.ganamnesis.features.profile.presentation.viewmodel.ProfileIntent
import com.cjthdev.ganamnesis.features.profile.presentation.viewmodel.ProfileViewModel
import org.koin.compose.koinInject

@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ProfileIntent.Load)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
    ) {
        Text(text = "Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            GanamnesisAvatar(
                avatarUrl = state.user?.avatarUrl,
                fallbackText = state.user?.username ?: "?",
                size = 72.dp,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = state.user?.username ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = state.user?.email ?: "",
                    fontSize = 14.sp,
                    color = Color.Gray,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Steam", fontWeight = FontWeight.Bold)
                Text(
                    text = if (state.user?.steamKey != null) "Linked" else "Not linked",
                    color = if (state.user?.steamKey != null) Color(0xFF2E7D32) else Color.Gray,
                )
            }
        }
    }
}
