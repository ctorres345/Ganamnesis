package com.cjthdev.ganamnesis.features.auth.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisTextField
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthEffect
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthIntent
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun ForgotPasswordScreen(onNavigateBack: () -> Unit) {
    val viewModel: AuthViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Forgot Password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Please enter your email address to reset your password",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
            )

            Spacer(modifier = Modifier.height(32.dp))

            GanamnesisTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                placeholder = "Enter your email address...",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                GanamnesisButton(
                    text = "Send Password",
                    onClick = { viewModel.handleIntent(AuthIntent.SendPasswordReset(email)) },
                )
            }

            if (state.isPasswordResetSent) {
                Text(
                    "Reset link sent! Please check your inbox.",
                    color = Color(0xFF43A047),
                    modifier = Modifier.padding(top = 16.dp),
                )
            }

            if (errorMessage != null) {
                Text(
                    errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onNavigateBack) {
                Text("Back to Sign In", color = Color.Gray)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.ShowError -> errorMessage = effect.message
                AuthEffect.NavigateToHome -> Unit
                AuthEffect.NavigateToLogin -> Unit
            }
        }
    }
}
