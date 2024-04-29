package com.example.planet.planetdetails.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.planet.NavigationItem

fun NavGraphBuilder.planetDtGraph(navController: NavHostController) {
    // Home Page
    composable(
        route = NavigationItem.DETAILS.route + "/{Id}",
        arguments = listOf(
            navArgument("Id") {
                type = NavType.StringType
                defaultValue = "Default"
            }
        ),
        enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
            )
        },
        popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(700)
            )
        },
    ) { backStackEntry ->
        PlanetDetailsScreen(
            onBackPressed = { navController.popBackStack() },
            backStackEntry.arguments?.getString("Id") ?: ""
        )
    }
}