package com.example.busapiclienttokenscompose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.ui.graphics.vector.ImageVector

val screensBottomBar = listOf(
    Screens("login_screen", "login-register", Icons.AutoMirrored.Filled.Login),
    Screens("driver_screen", "driver-account", Icons.AutoMirrored.Filled.Login),
    Screens("drivers_screen", "drivers-list", Icons.AutoMirrored.Filled.Login),
//    Screens("line_screen", "bus-line", Icons.AutoMirrored.Filled.Login),
//    Screens("stop_screen", "bus-stop", Icons.AutoMirrored.Filled.Login),
)

data class Screens(
    val route: String,
    val name: String,
    val icon: ImageVector,
)
