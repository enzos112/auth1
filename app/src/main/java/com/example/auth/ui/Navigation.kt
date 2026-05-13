package com.example.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.AuthState
import com.example.auth.AuthViewModel
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun AppNavigation(viewModel: AuthViewModel) {
    val navController = rememberNavController()
    val authState by viewModel.authState.collectAsState()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            LaunchedEffect(authState) {
                when (authState) {
                    is AuthState.Authenticated -> navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                    is AuthState.Unauthenticated -> navController.navigate("auth") {
                        popUpTo("splash") { inclusive = true }
                    }
                    else -> Unit
                }
            }
            SplashScreen()
        }
        composable("auth") {
            AuthScreen(viewModel)
        }
        composable("home") {
            LaunchedEffect(authState) {
                if (authState is AuthState.Unauthenticated) {
                    navController.navigate("auth") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
            val state = authState as? AuthState.Authenticated
            HomeScreen(email = state?.email ?: "", viewModel = viewModel)
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}