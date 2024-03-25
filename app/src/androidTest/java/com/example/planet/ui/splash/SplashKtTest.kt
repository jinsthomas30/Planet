package com.example.planet.ui.splash

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.planet.MainActivity
import com.example.planet.NavigationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class SplashKtTest {

    // Create a rule for testing Compose UI
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun splash() {
        // Set content to Splash
        composeTestRule.activity.setContent {
            // Create a NavController
            val navController = rememberNavController()
            Splash(navController = navController, modifier = Modifier)

            // Set delay 3000 milliseconds to simulate splash screen
            runBlocking {
                delay(3000)
                // Navigate to the Planet Listing page after the delay
                navController?.navigate(NavigationItem.PLANT_LIST.route) {
                    // Specify popUpTo to clear the back stack
                    popUpTo(NavigationItem.PLANT_LIST.route) {
                        inclusive = true
                    }
                }
            }
        }

    }

    @Test
    fun splashScreenView() {
        // Set content to SplashScreenView
        composeTestRule.activity.setContent {
            SplashScreenView(modifier = Modifier)
        }

        // Verify that Image and Text elements are displayed
        composeTestRule.onNodeWithContentDescription("Planet Icon").assertExists()
        composeTestRule.onNodeWithText("Planet").assertExists()
    }
}
