package com.example.planet.network

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.planet.R

@Composable
fun NetworkDependentScreen(
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    networkStateViewModel: NetworkStateViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val networkState by networkStateViewModel.networkState.collectAsState(initial = NetworkState.None)

    Box(modifier = modifier){
        OfflineDialog(networkState = networkState) { retryAction() }
        content()
    }
}
@Composable
fun OfflineDialog(
    networkState: NetworkState = NetworkState.None,
    onRetry: () -> Unit
) {
    if(networkState is NetworkState.NotConnected){
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = stringResource(R.string.network_connection_error)) },
            text = { Text(text = stringResource(R.string.network_error_message)) },
            confirmButton = {
                TextButton(onClick = onRetry) {
                    Text(stringResource(R.string.Retry))
                }
            }
        )
    }
}