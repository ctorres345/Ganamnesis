package com.cjthdev.ganamnesis

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cjthdev.ganamnesis.core.ui.theme.GanamnesisTheme
import com.cjthdev.ganamnesis.features.auth.presentation.ui.ForgotPasswordScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.LandingScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.SignInScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.SignUpScreen
import com.cjthdev.ganamnesis.features.dashboard.ui.DashboardScreen
import com.cjthdev.ganamnesis.features.setup.presentation.ui.*
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

enum class Screen(theRoute: String) {
    Landing("landing"),
    SignIn("signin"),
    SignUp("signup"),
    ForgotPassword("forgot_password"),
    SetupStart("setup_start"),
    SteamSetup("steam_setup"),
    RawgSetup("rawg_setup"),
    SearchGame("search_game"),
    LibraryPreview("library_preview"),
    Dashboard("dashboard");
    
    val route = theRoute
}

@Composable
fun App() {
    KoinContext {
        GanamnesisTheme {
            val navController = rememberNavController()
            val setupViewModel: SetupViewModel = koinInject()
            val setupState by setupViewModel.uiState.collectAsState()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Landing.route
                ) {
                    composable(Screen.Landing.route) {
                        LandingScreen(
                            onGetStarted = { navController.navigate(Screen.SetupStart.route) },
                            onSignIn = { navController.navigate(Screen.SignIn.route) }
                        )
                    }
                    composable(Screen.SignIn.route) {
                        SignInScreen(
                            onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                            onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                            onNavigateToHome = { navController.navigate(Screen.Dashboard.route) }
                        )
                    }
                    composable(Screen.SignUp.route) {
                        SignUpScreen(
                            onNavigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                            onNavigateToHome = { navController.navigate(Screen.Dashboard.route) }
                        )
                    }
                    composable(Screen.ForgotPassword.route) {
                        ForgotPasswordScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    
                    // Setup Wizard
                    composable(Screen.SetupStart.route) {
                        SetupStartScreen(
                            onSyncWithSteam = { navController.navigate(Screen.SteamSetup.route) },
                            onSyncWithRawg = { navController.navigate(Screen.RawgSetup.route) }
                        )
                    }
                    composable(Screen.SteamSetup.route) {
                        SteamSetupScreen(
                            onBack = { navController.popBackStack() },
                            onSyncStarted = { 
                                // Dialog is shown based on state, but we can navigate ahead
                                navController.navigate(Screen.LibraryPreview.route)
                            }
                        )
                    }
                    composable(Screen.RawgSetup.route) {
                        RawgSetupScreen(
                            onBack = { navController.popBackStack() },
                            onNext = { navController.navigate(Screen.SearchGame.route) }
                        )
                    }
                    composable(Screen.SearchGame.route) {
                        SearchGameScreen(
                            onBack = { navController.popBackStack() },
                            onNext = { navController.navigate(Screen.LibraryPreview.route) }
                        )
                    }
                    composable(Screen.LibraryPreview.route) {
                        LibraryPreviewScreen(
                            onFinish = { 
                                navController.navigate(Screen.Dashboard.route) {
                                    popUpTo(Screen.Landing.route) { inclusive = true }
                                }
                            }
                        )
                    }
                    
                    composable(Screen.Dashboard.route) {
                        DashboardScreen()
                    }
                }
                
                // Global Sync Progress Dialog
                SyncProgressDialog(
                    status = setupState.syncStatus,
                    onDismiss = { /* Optionally handle dismiss */ }
                )
            }
        }
    }
}
