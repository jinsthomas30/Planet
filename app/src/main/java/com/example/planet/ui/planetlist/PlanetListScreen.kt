package com.example.planet.ui.planetlist

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.ui.planetlist.data.PlanetItem
import kotlinx.coroutines.launch

@Composable
fun PlanetListScreen(navController: NavHostController, viewModel: PlanetListViewModel) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TopAppBar(navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController, viewModel: PlanetListViewModel) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val activity = (LocalContext.current as? Activity)
    //Back press event
    BackHandler(enabled = !backPressHandled) {
        println("back pressed")
        coroutineScope.launch {
            activity?.finish()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        stringResource(R.string.planet_list),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        activity?.finish()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "back arrow",
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        ScrollContent(innerPadding, viewModel)
    }

}

@Composable
fun ScrollContent(innerPadding: PaddingValues, viewModel: PlanetListViewModel) {
    IndeterminateCircularIndicator(viewModel)
    val planets by viewModel.planetList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = innerPadding,
    ) {
        items(planets) { planet: PlanetItem ->
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = planet.title)
                }
            }


        }
    }
}
@Composable
fun IndeterminateCircularIndicator(viewModel: PlanetListViewModel) {
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