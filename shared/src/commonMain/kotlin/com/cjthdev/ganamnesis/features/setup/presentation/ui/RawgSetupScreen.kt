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
fun RawgSetupScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val viewModel: SetupViewModel = koinInject()
    
    var key by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Sync with RAWG",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Setup RAWG to search and add games",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        GanamnesisTextField(
            value = key,
            onValueChange = { key = it },
            label = "RAWG API Key",
            placeholder = "My Key..."
        )

        Spacer(modifier = Modifier.height(32.dp))

        GanamnesisButton(
            text = "Continue",
            onClick = {
                viewModel.handleIntent(SetupIntent.UpdateRawgKey(key))
                onNext()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("Back", color = Color.Gray)
        }
    }
}
