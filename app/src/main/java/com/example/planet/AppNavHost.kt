package com.example.planet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.ui.planetlist.PlanetListScreen
import com.example.planet.ui.splash.Splash

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.SPLASH.route,

    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.SPLASH.route) {
            Splash(navController, modifier = modifier)
        }
        composable(NavigationItem.PLANT_LIST.route) {
            PlanetListScreen(navController)
        }
    }
}

