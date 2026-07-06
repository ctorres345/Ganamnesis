package com.cjthdev.ganamnesis.features.auth.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisButton
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisPasswordTextField
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisTextField
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthEffect
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthIntent
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun SignInScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToHome: (hasCompletedSetup: Boolean) -> Unit,
) {
    val viewModel: AuthViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                text = "Sign In",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Sign in to gain access to your gaming library",
                fontSize = 14.sp,
                color = Color.Gray,
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

            Spacer(modifier = Modifier.height(16.dp))

            GanamnesisPasswordTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "Enter your password...",
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onNavigateToForgotPassword) {
                    Text("Forgot Password", color = Color(0xFFF4511E))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage != null) {
                Text(
                    errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                GanamnesisButton(
                    text = "Sign In",
                    onClick = { viewModel.handleIntent(AuthIntent.Login(email, password)) },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("OR", color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            GanamnesisButton(
                text = "Sign In With Google",
                onClick = { viewModel.handleIntent(AuthIntent.LoginWithGoogle) },
                containerColor = Color.Black,
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account? ", color = Color.Gray)
                TextButton(onClick = onNavigateToSignUp) {
                    Text("Sign Up", color = Color(0xFFF4511E), fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.ShowError -> errorMessage = effect.message
                AuthEffect.NavigateToHome -> onNavigateToHome(state.user?.hasCompletedSetup ?: false)
                AuthEffect.NavigateToLogin -> Unit
            }
        }
    }
}
