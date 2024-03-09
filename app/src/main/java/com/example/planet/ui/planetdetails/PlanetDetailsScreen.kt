package com.example.planet.ui.planetdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.ui.planetdetails.data.MyDialog
import com.example.planet.ui.planetdetails.viewModel.PlanetDetailsViewModel

@Composable
fun PlanetDetailsScreen(
    navController: NavHostController,
    id: String,
    mPlanetDetailsViewModel: PlanetDetailsViewModel
) {
    mPlanetDetailsViewModel.fetchPlanetDt(id)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TopAppBar(navController, mPlanetDetailsViewModel)
        IndeterminateCircularIndicator(mPlanetDetailsViewModel)
        val state by mPlanetDetailsViewModel.openDialog.collectAsState()
        AlertDialogView(
            state = state,
            onDismiss = mPlanetDetailsViewModel::onDismiss, navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController, mPlanetDetailsViewModel: PlanetDetailsViewModel) {
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        stringResource(R.string.planet_details_page),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "back arrow",
                            tint = Color.White
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        DetailsContent(innerPadding, mPlanetDetailsViewModel)
    }

}

@Composable
fun DetailsContent(innerPadding: PaddingValues, mPlanetDetailsViewModel: PlanetDetailsViewModel) {
    val planetDetails by mPlanetDetailsViewModel.planetDetails.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = planetDetails.name.toString(),
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.diameter)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.diameter.toString()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.rotation_period)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.rotation_period.toString()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.orbital_period)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.orbital_period.toString()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.gravity)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.gravity.toString()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.population)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.population.toString()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.climate)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.climate.toString()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.terrain)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.terrain.toString()
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.surface_water)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = planetDetails.surface_water.toString()
                        )
                    }
                }


            }

        }
    }

}

@Composable
fun IndeterminateCircularIndicator(viewModel: PlanetDetailsViewModel) {
    val loading by viewModel.isLoading.collectAsState()
    if (!loading) return
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun AlertDialogView(
    state: MyDialog,
    onDismiss: () -> Unit, navController: NavHostController
) {
    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss
            },
            title = {
                Text(stringResource(id = R.string.alert_msg), fontSize = 16.sp)
            },
            confirmButton = {
                Button(
                    onClick = {
                        navController.popBackStack()
                    }) {
                    Text("Ok", fontSize = 16.sp)
                }
            }
        )

    }
}
