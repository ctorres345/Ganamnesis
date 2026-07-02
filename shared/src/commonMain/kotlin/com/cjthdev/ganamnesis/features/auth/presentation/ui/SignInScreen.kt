package com.cjthdev.ganamnesis.features.auth.presentation.ui

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
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthIntent
import com.cjthdev.ganamnesis.features.auth.presentation.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun SignInScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel: AuthViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "Sign In",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Sign in to gain access to your gaming library",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            GanamnesisTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                placeholder = "Enter your email address...",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GanamnesisTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "Enter your password...",
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onNavigateToForgotPassword) {
                    Text("Forgot Password", color = Color(0xFFF4511E))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                GanamnesisButton(
                    text = "Sign In",
                    onClick = { viewModel.handleIntent(AuthIntent.Login(email, password)) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text("OR", color = Color.Gray)
            
            Spacer(modifier = Modifier.height(24.dp))

            GanamnesisButton(
                text = "Sign In With Google",
                onClick = { viewModel.handleIntent(AuthIntent.LoginWithGoogle) },
                containerColor = Color.Black
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

    LaunchedEffect(state.user) {
        if (state.user != null) {
            onNavigateToHome()
        }
    }
}
