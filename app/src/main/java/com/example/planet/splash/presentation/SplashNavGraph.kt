package com.example.planet.splash.presentation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.planet.NavigationItem

fun NavGraphBuilder.splashGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Home Page
    composable(NavigationItem.SPLASH.route) {
        Splash(onNavigateToPlanetList = {
            navController.navigate(NavigationItem.PLANT_LIST.route) {
                popUpTo(NavigationItem.PLANT_LIST.route) {
                    inclusive = true
                }
            }
        }, modifier)
    }
}