package com.vasant.pillpal

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vasant.pillpal.ui.navigation.NavigationRoute
import com.vasant.pillpal.ui.screens.AddMedsScreen
import com.vasant.pillpal.ui.screens.HomeScreen

@Composable
fun  PillPalApp(){
    val navController=rememberNavController()
    NavHost(
        navController=navController
        , startDestination = NavigationRoute.HomeScreen
    ){
        composable<NavigationRoute.HomeScreen>{
            HomeScreen(navController)
        }
        composable<NavigationRoute.AddMedicineScreen>{
            AddMedsScreen(navController)
        }

    }
}
