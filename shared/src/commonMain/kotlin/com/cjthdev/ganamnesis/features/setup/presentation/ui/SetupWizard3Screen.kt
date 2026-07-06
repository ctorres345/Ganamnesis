package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupIntent
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import ganamnesis.shared.generated.resources.Res
import ganamnesis.shared.generated.resources.game_library
import org.jetbrains.compose.resources.painterResource

@Composable
fun SetupWizard3Screen(viewModel: SetupViewModel) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        WizardProgressIndicator(currentStep = 3, totalSteps = 3)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "All set!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You're all set! Head to your dashboard to start tracking your backlog, playtime, and achievements.",
            fontSize = 16.sp,
            color = Color.Gray,
        )

        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.game_library),
                contentDescription = "All set",
                modifier = Modifier.padding(8.dp),
            )
        }

        GanamnesisButton(
            text = "Continue",
            onClick = { viewModel.handleIntent(SetupIntent.CompleteSetup) },
        )
    }
}
