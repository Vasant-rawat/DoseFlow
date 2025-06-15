package com.vasant.pillpal.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationRoute {

    @Serializable
    data object HomeScreen : NavigationRoute

    @Serializable
    data object AddMedicineScreen : NavigationRoute

    @Serializable
    object SettingScreen : NavigationRoute

    @Serializable
    object ChatScreen : NavigationRoute
}