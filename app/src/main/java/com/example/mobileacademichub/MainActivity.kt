package com.example.mobileacademichub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobileacademichub.navigation.HomeNavGraph
import com.example.mobileacademichub.navigation.Screen
import com.example.mobileacademichub.ui.theme.MobileAcademicHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAcademicHubTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AcademicHubApp()
                }
            }
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Dashboard : BottomNavItem(Screen.Dashboard.route, Icons.Default.Dashboard, "Dashboard")
    object Schedule : BottomNavItem(Screen.Schedule.route, Icons.Default.CalendarMonth, "Jadwal")
    object Assignments : BottomNavItem(Screen.Assignments.route, Icons.Default.Assignment, "Tugas")
    object Chat : BottomNavItem(Screen.Chat.route, Icons.Default.Chat, "Chat")
}

@Composable
fun AcademicHubApp() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Schedule,
        BottomNavItem.Assignments,
        BottomNavItem.Chat
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {\ paddingValues ->
        HomeNavGraph(navController = navController, modifier = Modifier.padding(paddingValues))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileAcademicHubTheme {
        AcademicHubApp()
    }
}
