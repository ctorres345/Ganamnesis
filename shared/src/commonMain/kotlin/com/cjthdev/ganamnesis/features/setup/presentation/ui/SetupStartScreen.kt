package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import ganamnesis.shared.generated.resources.Res
import ganamnesis.shared.generated.resources.game_library_2
import org.jetbrains.compose.resources.painterResource

@Composable
fun SetupStartScreen(
    onSyncWithSteam: () -> Unit,
    onSyncWithRawg: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            NavigationButtons(
                onSyncWithSteam, onSyncWithRawg
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(all = 24.dp)
            ) {
                Text(
                    text = "Let's get Started",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Let's start with integrations",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.game_library_2),
                        contentDescription = "Game Library",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun NavigationButtons(
    onSyncWithSteam: () -> Unit,
    onSyncWithRawg: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GanamnesisButton(
            text = "Sync with Steam",
            onClick = onSyncWithSteam
        )

        Spacer(modifier = Modifier.height(16.dp))

        GanamnesisButton(
            text = "Sync with RAWG",
            onClick = onSyncWithRawg,
            containerColor = Color.White,
            contentColor = Color.Black
        )
    }
}
