package com.example.planet.planetlist.prensentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.planet.NavigationItem
import com.example.planet.R
import com.example.planet.common.presentation.components.DialogView
import com.example.planet.common.presentation.components.IndeterminateCircularIndicator
import com.example.planet.planetlist.data.PlanetEntity

@Composable
fun PlanetListScreen(onItemSelect: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MainScreen(onItemSelect)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onItemSelect: (String) -> Unit) {
    val backPressHandled by remember { mutableStateOf(false) }
    val activity = (LocalContext.current as? Activity)

    // Handle back press
    BackHandler(enabled = !backPressHandled) {
        activity?.finish()
    }

    Scaffold(
        topBar = {
            // Top app bar
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    // Title text
                    Text(
                        stringResource(R.string.planet_list),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    // Back button
                    IconButton(onClick = { activity?.finish() }) {
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
        // ViewModel initialization
        val planetListViewModel: PlanetListViewModel = hiltViewModel()

        // Collect states from the ViewModel
        val dialogState by planetListViewModel.dialogState.collectAsState()
        val loaderState by planetListViewModel.isLoading.collectAsState()
        val planets by planetListViewModel.planetList.collectAsState()

        // Fetch planet list when screen initializes
        LaunchedEffect(Unit) {
            planetListViewModel.fetchPlanetList()
        }

        // Display dialog if needed
        DialogView(
            dialogState = dialogState,
            onDismiss = { planetListViewModel.dismissDialog() }
        )

        // LazyColumn for displaying planet list
        PlanetListContent(innerPadding, planets, onItemSelect)

        // Display loading indicator if loading
        IndeterminateCircularIndicator(loaderState)
    }
}

@Composable
fun PlanetListContent(
    innerPadding: PaddingValues,
    planets: List<PlanetEntity>,
    onItemSelect: (String) -> Unit
) {
    // LazyColumn to display a list of planets
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = innerPadding,
    ) {
        items(planets) { planet ->
            // Planet item
            PlanetListItem(planet) { clickedPlanet ->
                onItemSelect(clickedPlanet.uid)
            }
            // Add spacer if it's the last item
            if (planet == planets.last()) Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

@Composable
fun PlanetListItem(
    planet: PlanetEntity,
    onItemClick: (PlanetEntity) -> Unit
) {
    // Display a planet item as an elevated card
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxSize(),
        onClick = {
            onItemClick(planet)
        }
    ) {
        // Row to display planet name
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = planet.name)
        }
    }
}
