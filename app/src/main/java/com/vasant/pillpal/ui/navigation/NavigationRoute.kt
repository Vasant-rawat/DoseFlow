package com.vasant.pillpal.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationRoute {
    @Serializable
    data object AuthScreens : NavigationRoute

    @Serializable
    data object MainScreens : NavigationRoute
}

@Serializable
sealed interface MainUiRoute {
    @Serializable
    data object HomeScreen : NavigationRoute

    @Serializable
    data object AddMedicineScreen : NavigationRoute

    @Serializable
    object SettingScreen : NavigationRoute

    @Serializable
    object ChatScreen : NavigationRoute
}

sealed interface AuthenticationRoute {
    @Serializable
    data object WelcomeScreen : AuthenticationRoute

    @Serializable
    data object LoginScreen : AuthenticationRoute

    @Serializable
    data object SingUpScreen : AuthenticationRoute


}