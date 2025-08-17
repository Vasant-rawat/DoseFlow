package com.vasant.pillpal.ui.navigation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.ActivityNavigator
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
    val context = LocalContext.current
    val prf= context.getSharedPreferences("login" ,MODE_PRIVATE)
    val isLoggedIn = prf.getBoolean("IS_LOGGED_IN",false)
    val startDestination = if(isLoggedIn){
        NavigationRoute.MainScreens
    }else{
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