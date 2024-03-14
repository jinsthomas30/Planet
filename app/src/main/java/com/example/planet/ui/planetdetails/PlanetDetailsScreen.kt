package com.example.planet.ui.planetdetails

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
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.ui.components.DialogView
import com.example.planet.ui.components.IndeterminateCircularIndicator
import com.example.planet.ui.components.connectivityStatus
import com.example.planet.ui.planetdetails.data.PlanetDtEntity
import com.example.planet.ui.planetdetails.viewModel.PlanetDetailsViewModel


@Composable
fun PlanetDetailsScreen(
    navController: NavHostController,
    id: String
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        DetailsPage(navController,id)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPage(navController: NavHostController,id: String) {
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        stringResource(R.string.planet_list),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "back arrow",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val planetDetailsViewModel: PlanetDetailsViewModel = hiltViewModel()
        val planetDetails by planetDetailsViewModel.planetDetails.collectAsState()
        val dialogState by planetDetailsViewModel.dialogState.collectAsState()
        val loaderState by planetDetailsViewModel.isLoading.collectAsState()
        if(connectivityStatus()){
            LaunchedEffect(Unit) {
                planetDetailsViewModel.fetchPlanetDt(id)
            }
        }else{
            LaunchedEffect(Unit) {
                planetDetailsViewModel.getPlanetDtFromDb(id)
            }
        }

        DialogView(
            dialogState = dialogState,
            onDismiss = { planetDetailsViewModel.dismissDialog() }
        )
        DetailsContent(innerPadding,planetDetails)
        IndeterminateCircularIndicator(loaderState)
    }

}

@Composable
fun DetailsContent(innerPadding: PaddingValues,planetDetails: PlanetDtEntity?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        planetDetails?.let { details ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextRow(label = "Name", value = details.name ?: "")
                TextRow(label = stringResource(id = R.string.diameter), value = details.diameter ?: "")
                TextRow(label = stringResource(id = R.string.rotation_period), value = details.rotation_period ?: "")
                TextRow(label = stringResource(id = R.string.orbital_period), value = details.orbital_period ?: "")
                TextRow(label = stringResource(id = R.string.gravity), value = details.gravity ?: "")
                TextRow(label = stringResource(id = R.string.population), value = details.population ?: "")
                TextRow(label = stringResource(id = R.string.climate), value = details.climate ?: "")
                TextRow(label = stringResource(id = R.string.terrain), value = details.terrain ?: "")
                TextRow(label = stringResource(id = R.string.surface_water), value = details.surface_water ?: "")
            }
        }
    }
}

@Composable
fun TextRow(label: String, value: String) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = label
            )
            Text(
                text = ": "
            )
            Text(
                modifier = Modifier.weight(1f),
                text = value
            )
        }
    }
}
