package com.vasant.pillpal.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.vasant.pillpal.ui.components.BottomNavigationBar
import com.vasant.pillpal.ui.components.HomeContent
import com.vasant.pillpal.ui.components.TopBarHomeScreen

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = { TopBarHomeScreen(navController) }, bottomBar = { BottomNavigationBar(navController) }
    ) {
        HomeContent(it)
    }
}





