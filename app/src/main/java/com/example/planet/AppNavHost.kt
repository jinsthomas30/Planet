package com.example.planet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.planetdetails.presentation.planetDtGraph
import com.example.planet.planetlist.prensentation.planetListGraph
import com.example.planet.splash.presentation.Splash
import com.example.planet.splash.presentation.splashGraph

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
        splashGraph(navController,modifier)
        planetListGraph(navController)
        planetDtGraph(navController)

    }
}


