package com.example.planet

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.planet.ui.planetdetails.PlanetDetailsScreen
import com.example.planet.ui.planetdetails.viewModel.PlanetDetailsViewModel
import com.example.planet.ui.planetlist.PlanetListScreen
import com.example.planet.ui.planetlist.viewModel.PlanetListViewModel
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
        composable(
            route =NavigationItem.DETAILS.route+"/{Id}",
            arguments = listOf(
                navArgument("Id") {
                    type = NavType.StringType
                    defaultValue = "Default"
                }
            ),enterTransition = {
                when (initialState.destination.route) {
                    "details" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "details" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
        ) { backStackEntry ->
            val mPlanetDetailsViewModel = hiltViewModel<PlanetDetailsViewModel>()
            PlanetDetailsScreen(navController,
                backStackEntry.arguments?.getString("Id") ?: "",mPlanetDetailsViewModel
            )
        }
    }
}


