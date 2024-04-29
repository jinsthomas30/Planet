package com.example.planet.ui.planetdetails
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.example.planet.MainActivity
import com.example.planet.planetdetails.presentation.BackButton
import com.example.planet.planetdetails.presentation.DetailsContent
import com.example.planet.planetdetails.presentation.DetailsPage
import com.example.planet.planetdetails.presentation.PlanetDetailsScreen
import com.example.planet.planetdetails.presentation.TextRow
import com.example.planet.planetdetails.presentation.TitleText
import com.example.planet.planetdetails.presentation.TopAppBar
import com.example.planet.planetdetails.data.PlanetDtEntity
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class PlanetDetailsScreenKtTest {

    // Create a rule for testing Compose UI
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun planetDetailsScreen() {
        // Test for PlanetDetailsScreen
        // Set content to PlanetDetailsScreen
        composeTestRule.activity.setContent {
            // Render PlanetDetailsScreen
            PlanetDetailsScreen(onBackPressed = {}, "1")
        }
        // Verify if the planet details title is displayed
        composeTestRule.onNodeWithText("Planet Details Page").assertExists()
    }

    @Test
    fun detailsPage() {
        // Test for DetailsPage
        // Set content to DetailsPage
        composeTestRule.activity.setContent {
            // Render DetailsPage
            DetailsPage(onBackPressed = {}, "1")
        }
        // Placeholder assertion
        assertTrue(true)
    }

    @Test
    fun topAppBar() {
        // Test for TopAppBar
        // Set content to TopAppBar
        composeTestRule.activity.setContent {
            // Render TopAppBar
            TopAppBar(onBackPressed = {})
        }
        // Verify if the planet details title is displayed in TopAppBar
        composeTestRule.onNodeWithText("Planet Details Page").assertExists()
    }

    @Test
    fun titleText() {
        // Test for TitleText
        // Set content to TitleText
        composeTestRule.activity.setContent {
            // Render TitleText
            TitleText()
        }
        // Verify if the planet details title is displayed
        composeTestRule.onNodeWithText("Planet Details Page").assertExists()
    }

    @Test
    fun backButton() {
        // Test for BackButton
        // Set content to BackButton
        composeTestRule.activity.setContent {
            // Render BackButton
            BackButton(onBackPressed = {})
        }
        // Verify that the back button is displayed
        composeTestRule.onNodeWithContentDescription("back arrow").assertExists()
    }

    @Test
    fun detailsContent() {
        // Test for DetailsContent
        // Set content to DetailsContent
        composeTestRule.activity.setContent {
            // Render DetailsContent
            DetailsContent(
                innerPadding = PaddingValues(16.dp),
                planetDetails = PlanetDtEntity(
                    name = "TestPlanet",
                    uid = "1",
                    created = "25/03/2024",
                    edited = "25/03/2024",
                    diameter = "10000 km",
                    rotation_period = "24 hours",
                    orbital_period = "365 days",
                    gravity = "9.8 m/s²",
                    population = "7.8 billion",
                    climate = "Temperate",
                    terrain = "Mountainous",
                    surface_water = "70%",
                    url = "www.planet.com/TestPlanet"
                )
            )
        }
        // Verify if the planet name is displayed in DetailsContent
        composeTestRule.onNodeWithText("TestPlanet").assertExists()
    }

    @Test
    fun textRow() {
        // Test for TextRow
        // Set content to TextRow
        composeTestRule.activity.setContent {
            // Render TextRow
            TextRow(label = "Name", value = "TestPlanet")
        }
        // Verify if the planet name is displayed in TextRow
        composeTestRule.onNodeWithText("TestPlanet").assertExists()
    }
}