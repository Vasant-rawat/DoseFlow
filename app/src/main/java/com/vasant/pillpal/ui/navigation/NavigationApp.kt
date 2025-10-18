package com.vasant.pillpal.ui.navigation

import android.content.Context.MODE_PRIVATE
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.vasant.pillpal.ui.screens.AddMedsScreen
import com.vasant.pillpal.ui.screens.AuthScreens.SignIn
import com.vasant.pillpal.ui.screens.AuthScreens.SignUpScreen
import com.vasant.pillpal.ui.screens.AuthScreens.WelcomeScreen
import com.vasant.pillpal.ui.screens.HomeScreen
import com.vasant.pillpal.ui.screens.NotificationsScreen
import com.vasant.pillpal.ui.screens.SettingsScreen

@Composable
fun NavigationApp(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val prf = context.getSharedPreferences("login", MODE_PRIVATE)
    val isLoggedIn = prf.getBoolean("IS_LOGGED_IN", false)
    val startDestination = if (isLoggedIn) {
        NavigationRoute.MainScreens
    } else {
        NavigationRoute.AuthScreens
    }

    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        navigation<NavigationRoute.AuthScreens>(startDestination = AuthenticationRoute.WelcomeScreen) {


            composable<AuthenticationRoute.LoginScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(900)
                    )
                }
            ) {
                SignIn(navController)
            }

            composable<AuthenticationRoute.SingUpScreen>(

                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(900)
                    )
                }


            ) {
                SignUpScreen(navController, windowSizeClass = windowSizeClass)
            }

            composable<AuthenticationRoute.WelcomeScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(700)
                    )
                }
            ) {
                WelcomeScreen(navController, windowSizeClass = windowSizeClass)
            }
        }
        navigation<NavigationRoute.MainScreens>(MainUiRoute.HomeScreen) {
            composable<MainUiRoute.HomeScreen>(enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(700)
                )
            }) {
                HomeScreen(navController)
            }
            composable<MainUiRoute.AddMedicineScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(700)
                    )
                },
            )
            {
                AddMedsScreen(navController)
            }
            composable<MainUiRoute.ChatScreen> { }
            composable<MainUiRoute.NotificationScreen> {

                NotificationsScreen(navController)
            }
            composable<MainUiRoute.SettingScreen> {
                SettingsScreen(navController)
            }
            composable<MainUiRoute.ProfileScreen> { }
        }

    }


}