package com.cjthdev.ganamnesis

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cjthdev.ganamnesis.features.auth.presentation.ui.LandingScreen

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    LandingScreen(
        onGetStarted = {},
        onSignIn = {}
    )
}