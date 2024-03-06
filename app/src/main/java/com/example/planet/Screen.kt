package com.example.planet

enum class Screen {
     SPLASH,
     PLANT_LIST,
     DETAILS,
}
sealed class NavigationItem(val route: String) {

    object SPLASH : NavigationItem(Screen.SPLASH.name)
    object PLANT_LIST : NavigationItem(Screen.PLANT_LIST.name)
    object DETAILS : NavigationItem(Screen.DETAILS.name)
}