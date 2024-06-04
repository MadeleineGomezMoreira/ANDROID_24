package com.example.busapiclienttokenscompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.busapiclienttokenscompose.R
import com.example.busapiclienttokenscompose.ui.common.BottomBar
import com.example.busapiclienttokenscompose.ui.common.TopBar
import com.example.busapiclienttokenscompose.ui.screens.driver.DriverScreen
import com.example.busapiclienttokenscompose.ui.screens.drivers.DriversScreen
import com.example.busapiclienttokenscompose.ui.screens.line.LineScreen
import com.example.busapiclienttokenscompose.ui.screens.login.LoginScreen
import com.example.busapiclienttokenscompose.ui.screens.stop.StopScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login_screen",
    ) {
        composable("login_screen") {
            LoginScreen(
                onLoginSuccessful = { userId ->
                    navController.navigate("driver_screen/$userId")
                },
                topBar = {
                    TopBar(
                        title = stringResource(id = R.string.login_register_upper)
                    )
                }
            )
        }
        composable(
            route = "driver_screen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val retrievedUserId = backStackEntry.arguments?.getString("userId")
            if (retrievedUserId != null) {
                DriverScreen(
                    userId = retrievedUserId.toInt(),
                    topBar = {
                        TopBar(
                            title = stringResource(id = R.string.driver_screen_upper)
                        )
                    },
                    bottomNavigationBar = {
                        BottomBar(
                            navController = navController,
                            screens = screensBottomBar,
                        )
                    },
                    onLineClicked = {
                        navController.navigate("line_screen")
                    }
                )
            }
        }
        composable("drivers_screen") {
            DriversScreen(
                topBar = {
                    TopBar(
                        title = stringResource(id = R.string.drivers_screen_upper)
                    )
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }
        composable("line_screen") {
            LineScreen(
                topBar = {
                    TopBar(
                        title = stringResource(id = R.string.line_screen_upper)
                    )
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }
        composable("stop_screen") {
            StopScreen(
                topBar = {
                    TopBar(
                        title = stringResource(id = R.string.stop_screen_upper)
                    )
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }
    }
}