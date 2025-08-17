package com.vasant.pillpal.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vasant.pillpal.ui.components.BottomNavigationBar
import com.vasant.pillpal.ui.components.HomeContent
import com.vasant.pillpal.ui.components.TopBarHomeScreen
import com.vasant.pillpal.ui.viewmodel.MedicineViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = { TopBarHomeScreen(navController) }, bottomBar = { BottomNavigationBar(navController) }
    ) {
        HomeContent(navController = navController,it)
    }
}





