package com.cjthdev.ganamnesis

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cjthdev.ganamnesis.core.ui.components.BottomNavItem
import com.cjthdev.ganamnesis.core.ui.components.GanamnesisBottomNavigationBar
import com.cjthdev.ganamnesis.core.ui.theme.GanamnesisTheme
import com.cjthdev.ganamnesis.features.auth.domain.repository.AuthRepository
import com.cjthdev.ganamnesis.features.auth.presentation.ui.ForgotPasswordScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.LandingScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.SignInScreen
import com.cjthdev.ganamnesis.features.auth.presentation.ui.SignUpScreen
import com.cjthdev.ganamnesis.features.backlog.ui.BacklogChallengeScreen
import com.cjthdev.ganamnesis.features.dashboard.ui.DashboardScreen
import com.cjthdev.ganamnesis.features.library.ui.LibraryScreen
import com.cjthdev.ganamnesis.features.profile.ui.ProfileScreen
import com.cjthdev.ganamnesis.features.settings.ui.SettingsScreen
import com.cjthdev.ganamnesis.features.setup.presentation.ui.*
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupEffect
import com.cjthdev.ganamnesis.features.setup.presentation.viewmodel.SetupViewModel
import kotlinx.coroutines.flow.first
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

enum class Screen(
    theRoute: String,
) {
    Landing("landing"),
    SignIn("signin"),
    SignUp("signup"),
    ForgotPassword("forgot_password"),
    SetupWizard1("setup_wizard_1"),
    SteamIntegration("steam_integration"),
    SetupWizard2("setup_wizard_2"),
    AddGame("add_game"),
    SetupWizard3("setup_wizard_3"),
    Dashboard("dashboard"),
    Library("library"),
    BacklogChallenge("backlog_challenge"),
    Profile("profile"),
    Settings("settings"),
    SteamResync("steam_resync"),
    ;

    val route = theRoute
}

private val bottomNavItems =
    listOf(
        BottomNavItem(Screen.Dashboard.route, "Dashboard", Icons.Filled.Home),
        BottomNavItem(Screen.Library.route, "Library", Icons.Filled.VideogameAsset),
        BottomNavItem(Screen.BacklogChallenge.route, "Challenge", Icons.Filled.EmojiEvents),
        BottomNavItem(Screen.Profile.route, "Profile", Icons.Filled.Person),
        BottomNavItem(Screen.Settings.route, "Settings", Icons.Filled.Settings),
    )

@Composable
fun App() {
    KoinContext {
        GanamnesisTheme {
            val authRepository: AuthRepository = koinInject()
            var startDestination by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                val user = authRepository.getCurrentUser().first()
                startDestination =
                    when {
                        user == null -> Screen.Landing.route
                        user.hasCompletedSetup -> Screen.Dashboard.route
                        else -> Screen.SetupWizard1.route
                    }
            }

            val resolvedStartDestination = startDestination
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                if (resolvedStartDestination == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    val navController = rememberNavController()
                    val setupViewModel: SetupViewModel = koinInject()

                    LaunchedEffect(Unit) {
                        setupViewModel.effect.collect { effect ->
                            when (effect) {
                                SetupEffect.NavigateToDashboard -> {
                                    navController.navigate(Screen.Dashboard.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                                is SetupEffect.ShowError -> Unit
                            }
                        }
                    }

                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStackEntry?.destination?.route
                    val showBottomBar = bottomNavItems.any { it.route == currentRoute }

                    Scaffold(
                        bottomBar = {
                            if (showBottomBar) {
                                GanamnesisBottomNavigationBar(
                                    items = bottomNavItems,
                                    currentRoute = currentRoute,
                                    onItemSelected = { item ->
                                        navController.navigate(item.route) {
                                            popUpTo(Screen.Dashboard.route) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                )
                            }
                        },
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = resolvedStartDestination,
                            modifier = Modifier.padding(innerPadding),
                        ) {
                            composable(Screen.Landing.route) {
                                LandingScreen(
                                    onGetStarted = { navController.navigate(Screen.SignUp.route) },
                                    onSignIn = { navController.navigate(Screen.SignIn.route) },
                                )
                            }
                            composable(Screen.SignIn.route) {
                                SignInScreen(
                                    onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                                    onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                                    onNavigateToHome = { hasCompletedSetup ->
                                        val destination = if (hasCompletedSetup) Screen.Dashboard.route else Screen.SetupWizard1.route
                                        navController.navigate(destination) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                )
                            }
                            composable(Screen.SignUp.route) {
                                SignUpScreen(
                                    onNavigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                                    onNavigateToHome = { hasCompletedSetup ->
                                        val destination = if (hasCompletedSetup) Screen.Dashboard.route else Screen.SetupWizard1.route
                                        navController.navigate(destination) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                )
                            }
                            composable(Screen.ForgotPassword.route) {
                                ForgotPasswordScreen(
                                    onNavigateBack = { navController.popBackStack() },
                                )
                            }

                            // Setup Wizard
                            composable(Screen.SetupWizard1.route) {
                                SetupWizard1Screen(
                                    onSync = { navController.navigate(Screen.SteamIntegration.route) },
                                    onSkip = { navController.navigate(Screen.SetupWizard2.route) },
                                )
                            }
                            composable(Screen.SteamIntegration.route) {
                                SteamIntegrationScreen(
                                    viewModel = setupViewModel,
                                    onBack = { navController.popBackStack() },
                                    onSuccess = { navController.navigate(Screen.SetupWizard2.route) },
                                )
                            }
                            composable(Screen.SetupWizard2.route) {
                                SetupWizard2Screen(
                                    onAddGame = { navController.navigate(Screen.AddGame.route) },
                                    onSkip = { navController.navigate(Screen.SetupWizard3.route) },
                                )
                            }
                            composable(Screen.AddGame.route) {
                                AddGameScreen(
                                    viewModel = setupViewModel,
                                    onBack = { navController.popBackStack() },
                                    onDone = { navController.navigate(Screen.SetupWizard3.route) },
                                )
                            }
                            composable(Screen.SetupWizard3.route) {
                                SetupWizard3Screen(viewModel = setupViewModel)
                            }

                            // Main tabs
                            composable(Screen.Dashboard.route) {
                                DashboardScreen(
                                    onNavigateToLibrary = { navController.navigate(Screen.Library.route) },
                                    onNavigateToBacklogChallenge = { navController.navigate(Screen.BacklogChallenge.route) },
                                )
                            }
                            composable(Screen.Library.route) {
                                LibraryScreen()
                            }
                            composable(Screen.BacklogChallenge.route) {
                                BacklogChallengeScreen()
                            }
                            composable(Screen.Profile.route) {
                                ProfileScreen()
                            }
                            composable(Screen.Settings.route) {
                                SettingsScreen(
                                    onNavigateToSteamSync = { navController.navigate(Screen.SteamResync.route) },
                                    onLoggedOut = {
                                        navController.navigate(Screen.Landing.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                )
                            }

                            // Steam key re-sync, triggered from Settings — reuses the onboarding
                            // screen but always returns to Settings instead of advancing the wizard.
                            composable(Screen.SteamResync.route) {
                                SteamIntegrationScreen(
                                    viewModel = setupViewModel,
                                    onBack = { navController.popBackStack() },
                                    onSuccess = { navController.popBackStack() },
                                    showProgress = false,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
