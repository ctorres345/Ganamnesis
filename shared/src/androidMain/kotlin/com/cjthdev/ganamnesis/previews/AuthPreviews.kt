package com.cjthdev.ganamnesis.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cjthdev.ganamnesis.core.ui.theme.GanamnesisTheme
import com.cjthdev.ganamnesis.features.auth.presentation.ui.ForgotPasswordScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.LandingScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.SignInScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.SignUpScreen

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    GanamnesisTheme {
        LandingScreen(
            onGetStarted = {},
            onSignIn = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    GanamnesisTheme {
        PreviewKoinScope {
            SignInScreen(
                onNavigateToSignUp = {},
                onNavigateToForgotPassword = {},
                onNavigateToHome = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    GanamnesisTheme {
        PreviewKoinScope {
            SignUpScreen(
                onNavigateToSignIn = {},
                onNavigateToHome = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    GanamnesisTheme {
        PreviewKoinScope {
            ForgotPasswordScreen(
                onNavigateBack = {},
            )
        }
    }
}
