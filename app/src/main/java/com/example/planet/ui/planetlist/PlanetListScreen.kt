package com.example.planet.ui.planetlist

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.planet.NavigationItem
import com.example.planet.R
import com.example.planet.ui.planetdetails.data.MyDialog
import com.example.planet.ui.planetlist.data.PlanetEntity
import com.example.planet.ui.planetlist.viewModel.PlanetListViewModel

@Composable
fun PlanetListScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TopAppBar(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
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
        activity?.let { ScrollContent(innerPadding, navController, it) }
    }

}

@Composable
fun ScrollContent(
    innerPadding: PaddingValues,
    navController: NavHostController,
    activity:Activity
) {
    val viewModel: PlanetListViewModel = hiltViewModel()
    val dialogState by viewModel.openDialog.collectAsState()
    DialogView(dialogState, viewModel::onDismiss, activity)
    IndeterminateCircularIndicator(viewModel)
    val planets by viewModel.planetList.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = innerPadding,
    ) {
        items(planets) { planetItem: PlanetEntity ->
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .fillMaxSize(),
                onClick = {
                    navController.navigate(NavigationItem.DETAILS.route + "/${planetItem.uid}") {
                        popUpTo(NavigationItem.PLANT_LIST.route)
                    }
                }
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Text(text = planetItem.name)
                }
            }
            if (planetItem == planets.last()) Spacer(modifier = Modifier.padding(bottom = 8.dp))
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

@Composable
fun DialogView(
    dialogState: MyDialog,
    onDismiss: () -> Unit,
    activity: Activity
) {
    if (dialogState.showDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = {
                Text(stringResource(id = R.string.alert_msg), fontSize = 16.sp)
            },
            confirmButton = {
                Button(
                    onClick = {
                        activity.finish()
                    }) {
                    Text(stringResource(id = R.string.ok), fontSize = 16.sp)
                }
            }
        )
    }
}