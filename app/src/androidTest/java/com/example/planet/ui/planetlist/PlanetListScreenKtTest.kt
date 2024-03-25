package com.example.planet.ui.planetlist
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.planet.MainActivity
import com.example.planet.NavigationItem
import com.example.planet.ui.planetlist.data.PlanetEntity
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class PlanetListScreenKtTest {

    // Create a rule for testing Compose UI
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun planetListScreen() {
        // Set content to PlanetListScreen
        composeTestRule.activity.setContent {
            // Create a NavController
            val navController = rememberNavController()
            MainScreen(navController = navController)
        }

        // Verify if the planet list title is displayed
        composeTestRule.onNodeWithText("Planet List").assertExists()
    }

    @Test
    fun mainScreen() {
        // Set content to MainScreen
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            MainScreen(navController)
        }

        // Verify if the back arrow button is displayed
        composeTestRule.onNodeWithContentDescription("back arrow").assertExists()
    }

    @Test
    fun planetListContent() {
        // Set up a list of planets
        val planets = listOf(
            PlanetEntity("1", "Mercury","www.planet.com/mercury"),
            PlanetEntity("2", "Venus","www.planet.com/venus"),
            PlanetEntity("3", "Earth","www.planet.com/earth")
        )

        // Set content to PlanetListContent
            composeTestRule.activity.setContent {
                val navController = rememberNavController()
            PlanetListContent(
                innerPadding = PaddingValues(0.dp),
                planets = planets,
                navController = navController
            )

                // Verify if the planets are displayed
                composeTestRule.onNodeWithText("Mercury").assertExists()
                composeTestRule.onNodeWithText("Venus").assertExists()
                composeTestRule.onNodeWithText("Earth").assertExists()

                // Simulate clicking on the first planet item
                composeTestRule.onNodeWithText("Mercury").performClick()

                // Verify that navigation occurred correctly
                assertEquals(NavigationItem.DETAILS.route + "/1", navController.currentDestination?.route)
        }
    }

    @Test
    fun planetListItem() {
        // Set up a planet
        val planet = PlanetEntity("1", "Mercury", "www.planet.com/mercury")

        // Set content to PlanetListItem
        composeTestRule.activity.setContent {
            PlanetListItem(planet) {}
        }

        // Verify if the planet name is displayed
        composeTestRule.onNodeWithText("Mercury").assertExists()
    }
}
