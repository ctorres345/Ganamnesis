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
fun SignUpScreen(
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel: AuthViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "Sign Up",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Sign up for a personalized gaming library",
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

            Spacer(modifier = Modifier.height(16.dp))

            GanamnesisTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                placeholder = "Repeat your password...",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                GanamnesisButton(
                    text = "Sign Up",
                    onClick = { 
                        if (password == confirmPassword) {
                            viewModel.handleIntent(AuthIntent.SignUp(email, password))
                        } else {
                            // Local validation for password match
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("I already have an account. ", color = Color.Gray)
                TextButton(onClick = onNavigateToSignIn) {
                    Text("Sign In", color = Color(0xFFF4511E), fontWeight = FontWeight.Bold)
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
