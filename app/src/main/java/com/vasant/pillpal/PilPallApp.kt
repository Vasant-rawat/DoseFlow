package com.vasant.pillpal

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vasant.pillpal.ui.navigation.NavigationRoute
import com.vasant.pillpal.ui.screens.AddMedsScreen
import com.vasant.pillpal.ui.screens.HomeScreen
import dagger.hilt.android.HiltAndroidApp

@Composable

fun DoseFlow() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = NavigationRoute.HomeScreen
    ) {
        composable<NavigationRoute.HomeScreen> {
            HomeScreen(navController)
        }
        composable<NavigationRoute.AddMedicineScreen> {
            AddMedsScreen(navController)

        }

    }
}

const val MEDICINE_CHANNEL_ID = "medicine_channel"
const val MEDICINE_CHANNEL_NAME = " DoseFlow  Notifications"

@HiltAndroidApp
class DoseFlowApp() : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MEDICINE_CHANNEL_ID,
                MEDICINE_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }
}