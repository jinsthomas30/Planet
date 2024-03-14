package com.example.planet.ui.planetlist

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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.planet.NavigationItem
import com.example.planet.R
import com.example.planet.ui.components.ConnectivityStatus
import com.example.planet.ui.components.DialogView
import com.example.planet.ui.components.IndeterminateCircularIndicator
import com.example.planet.ui.planetlist.data.PlanetEntity
import com.example.planet.ui.planetlist.viewModel.PlanetListViewModel

@Composable
fun PlanetListScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MainScreen(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val backPressHandled by remember { mutableStateOf(false) }
    val activity = (LocalContext.current as? Activity)

    BackHandler(enabled = !backPressHandled) {
        activity?.finish()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        stringResource(R.string.planet_list),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
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
        val planetListViewModel: PlanetListViewModel = hiltViewModel()
        val dialogState by planetListViewModel.dialogState.collectAsState()
        val loaderState by planetListViewModel.isLoading.collectAsState()
        val planets by planetListViewModel.planetList.collectAsState()
        if(ConnectivityStatus()){
            LaunchedEffect(Unit) {
                planetListViewModel.fetchPlanetList()
            }
        }else{
            LaunchedEffect(Unit) {
                planetListViewModel.fetchDataFromDb()
            }
        }

        DialogView(
            dialogState = dialogState,
            onDismiss = { planetListViewModel.dismissDialog() }
        )
        LazyColumnInitialize(innerPadding, planets) { itemClick ->
            navController.navigate(NavigationItem.DETAILS.route + "/${itemClick.uid}") {
                popUpTo(NavigationItem.PLANT_LIST.route)
            }
        }
        IndeterminateCircularIndicator(loaderState)
    }

}

@Composable
fun LazyColumnInitialize(
    innerPadding: PaddingValues,
    planets: List<PlanetEntity>,
    onItemClick: (PlanetEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = innerPadding,
    ) {
        items(planets) { planetEntity: PlanetEntity ->
            PlanetItem(onItemClick, planetEntity)
            if (planetEntity == planets.last()) Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

@Composable
fun PlanetItem(
    onItemClick: (PlanetEntity) -> Unit,
    planetItem: PlanetEntity
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxSize(),
        onClick = {
            onItemClick(planetItem)
        }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = planetItem.name)
        }
    }

}

