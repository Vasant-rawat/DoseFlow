package com.vasant.pillpal.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.vasant.pillpal.ui.screens.AddMedsScreen
import com.vasant.pillpal.ui.screens.AuthScreens.SignIn
import com.vasant.pillpal.ui.screens.AuthScreens.SignUpScreen
import com.vasant.pillpal.ui.screens.AuthScreens.WelcomeScreen
import com.vasant.pillpal.ui.screens.HomeScreen


@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = NavigationRoute.AuthScreens
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
                SignUpScreen(navController)
            }

            composable<AuthenticationRoute.WelcomeScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(700)
                    )
                }
            ) {
                WelcomeScreen(navController)
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
            composable<MainUiRoute.AddMedicineScreen>(enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(700)
                )
            })
            {
                AddMedsScreen(navController)
            }
            composable<MainUiRoute.ChatScreen> { }
            composable<MainUiRoute.SettingScreen> { }
        }

    }


}