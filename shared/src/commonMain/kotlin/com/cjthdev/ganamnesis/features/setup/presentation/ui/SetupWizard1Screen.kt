package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import com.cjthdev.ganamnesis.core.ui.components.WizardProgressIndicator
import ganamnesis.shared.generated.resources.Res
import ganamnesis.shared.generated.resources.game_library_2
import org.jetbrains.compose.resources.painterResource

@Composable
fun SetupWizard1Screen(
    onSync: () -> Unit,
    onSkip: () -> Unit,
) {
    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        bottomBar = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GanamnesisButton(text = "Sync", onClick = onSync)
                Spacer(modifier = Modifier.height(16.dp))
                GanamnesisButton(
                    text = "Skip",
                    onClick = onSkip,
                    containerColor = Color.White,
                    contentColor = Color.Black,
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(all = 24.dp),
            ) {
                WizardProgressIndicator(currentStep = 1, totalSteps = 3)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Sync your Steam Library",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "For a better experience let's sync your steam library. This will help you track your games, played hours and achievements",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(Res.drawable.game_library_2),
                        contentDescription = "Sync your Steam library",
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        },
    )
}
