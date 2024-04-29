package com.example.planet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.planetdetails.presentation.planetDtGraph
import com.example.planet.planetlist.prensentation.planetListGraph
import com.example.planet.splash.presentation.Splash

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
        planetListGraph(navController)
        planetDtGraph(navController)

    }
}


