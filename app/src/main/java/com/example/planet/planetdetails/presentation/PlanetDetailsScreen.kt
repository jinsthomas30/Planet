package com.example.planet.planetdetails.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.planet.R
import com.example.planet.common.presentation.components.DialogView
import com.example.planet.common.presentation.components.IndeterminateCircularIndicator
import com.example.planet.planetdetails.data.PlanetDtEntity


/**
 * Composable function for displaying the screen for viewing planet details.
 *
 * @param onBackPressed The onBackPressed used for back navigation.
 * @param id The ID of the planet to display details for.
 */
@Composable
fun PlanetDetailsScreen(
    onBackPressed: () -> Unit,
    id: String
) {
    // Surface to contain the details page
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Display the details page
        DetailsPage(onBackPressed, id)
    }
}

/**
 * Composable function for displaying the details page.
 *
 * @param onBackPressed used for back navigation.
 * @param id The ID of the planet to display details for.
 */
@Composable
fun DetailsPage( onBackPressed: () -> Unit, id: String) {
    // Scaffold to provide basic layout structure
    Scaffold(
        topBar = {
            // Top app bar with title and back button
            TopAppBar(onBackPressed)
        },
    ) { innerPadding ->
        // ViewModel initialization
        val planetDetailsViewModel: PlanetDetailsViewModel = hiltViewModel()
        // Collect states from the ViewModel
        val planetDetails by planetDetailsViewModel.planetDetails.collectAsState()
        val dialogState by planetDetailsViewModel.dialogState.collectAsState()
        val loaderState by planetDetailsViewModel.isLoading.collectAsState()

        // Fetch planet details when the screen initializes
        LaunchedEffect(Unit) {
            planetDetailsViewModel.fetchPlanetDetails(id)
        }

        // Display dialog if needed
        DialogView(
            dialogState = dialogState,
            onDismiss = { planetDetailsViewModel.dismissDialog() }
        )

        // Display planet details content
        DetailsContent(innerPadding, planetDetails)

        // Display loading indicator if loading
        IndeterminateCircularIndicator(loaderState)
    }

}

/**
 * Composable function to display the top app bar.
 *
 * @param onBackPressed used for back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(onBackPressed: () -> Unit) {
    androidx.compose.material3.TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            TitleText()
        },
        navigationIcon = {
            BackButton(onBackPressed)
        },
    )
}

/**
 * Composable function to display the title text in the top app bar.
 */
@Composable
fun TitleText() {
    Text(
        stringResource(R.string.planet_details_page),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

/**
 * Composable function to display the back button in the top app bar.
 *
 * @param onBackPressed used for back navigation.
 */
@Composable
fun BackButton(onBackPressed: () -> Unit) {
    IconButton(onClick = { onBackPressed() }) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
            contentDescription = "back arrow",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

/**
 * Composable function for displaying the content of the planet details screen.
 *
 * @param innerPadding Inner padding applied by the parent composable.
 * @param planetDetails The details of the planet to display.
 */
@Composable
fun DetailsContent(innerPadding: PaddingValues, planetDetails: PlanetDtEntity?) {
    // Container box for details content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // Check if planet details are available
        planetDetails?.let { details ->
            // Display planet details in a column layout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display each detail row
                TextRow(label = "Name", value = details.name ?: "")
                TextRow(
                    label = stringResource(id = R.string.diameter),
                    value = details.diameter ?: ""
                )
                TextRow(
                    label = stringResource(id = R.string.rotation_period),
                    value = details.rotation_period ?: ""
                )
                TextRow(
                    label = stringResource(id = R.string.orbital_period),
                    value = details.orbital_period ?: ""
                )
                TextRow(
                    label = stringResource(id = R.string.gravity),
                    value = details.gravity ?: ""
                )
                TextRow(
                    label = stringResource(id = R.string.population),
                    value = details.population ?: ""
                )
                TextRow(
                    label = stringResource(id = R.string.climate),
                    value = details.climate ?: ""
                )
                TextRow(
                    label = stringResource(id = R.string.terrain),
                    value = details.terrain ?: ""
                )
                TextRow(
                    label = stringResource(id = R.string.surface_water),
                    value = details.surface_water ?: ""
                )
            }
        }
    }
}

/**
 * Composable function for displaying a row of text.
 *
 * @param label The label for the text row.
 * @param value The value to display in the text row.
 */
@Composable
fun TextRow(label: String, value: String) {
    // Column layout for text row
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        // Row layout for label and value
        Row {
            // Label text
            Text(
                modifier = Modifier.weight(1f),
                text = label
            )
            // Separator
            Text(text = ": ")
            // Value text
            Text(
                modifier = Modifier.weight(1f),
                text = value
            )
        }
    }
}
