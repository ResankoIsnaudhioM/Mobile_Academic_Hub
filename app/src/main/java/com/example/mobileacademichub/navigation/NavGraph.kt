package com.example.mobileacademichub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileacademichub.ui.screens.AssignmentScreen
import com.example.mobileacademichub.ui.screens.ChatScreen
import com.example.mobileacademichub.ui.screens.DashboardScreen
import com.example.mobileacademichub.ui.screens.ScheduleScreen
import androidx.compose.ui.Modifier

object Graph {
    const val HOME = "home_graph"
    const val AUTHENTICATION = "auth_graph"
}

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Schedule : Screen("schedule")
    object Assignments : Screen("assignments")
    object Chat : Screen("chat")
    object Login : Screen("login") // Placeholder for authentication
}

@Composable
fun HomeNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, route = Graph.HOME, startDestination = Screen.Dashboard.route, modifier = modifier) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(modifier = modifier)
        }
        composable(Screen.Schedule.route) {
            ScheduleScreen(modifier = modifier)
        }
        composable(Screen.Assignments.route) {
            AssignmentScreen(modifier = modifier)
        }
        composable(Screen.Chat.route) {
            ChatScreen(modifier = modifier)
        }
        // TODO: Add other screens as needed
    }
}
