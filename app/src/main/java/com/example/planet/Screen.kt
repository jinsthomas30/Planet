package com.example.planet

enum class Screen {
    SPLASH,
    PLANT_LIST,
    DETAILS,
}

sealed class NavigationItem(val route: String) {

    data object SPLASH : NavigationItem(Screen.SPLASH.name)
    data object PLANT_LIST : NavigationItem(Screen.PLANT_LIST.name)
    data object DETAILS : NavigationItem(Screen.DETAILS.name)
}