package com.denisatrif.truthdare.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.EntryScreen.route) {
        composable(route = Screen.EntryScreen.route) {
            ComposeEntryScreen(navController = navController)
        }
        composable(route = Screen.PlayersScreen.route) {
            ComposablePlayersScreen()
        }
    }
}

sealed class Screen(val route: String) {
    object EntryScreen : Screen("entry_screen")
    object PlayersScreen : Screen("players_screen")
}

fun NavHostController.navigateAndClean(route: String) {
    navigate(route = route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
    graph.setStartDestination(route)
}