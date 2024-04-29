package com.example.planet.planetlist.prensentation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.planet.NavigationItem

fun NavGraphBuilder.planetListGraph(navController: NavHostController) {
    // Home Page
    composable(NavigationItem.PLANT_LIST.route) {
        PlanetListScreen(onItemSelect = {
            navController.navigate(NavigationItem.DETAILS.route + "/${it}") {
                popUpTo(NavigationItem.PLANT_LIST.route)
            }
        })
    }
}