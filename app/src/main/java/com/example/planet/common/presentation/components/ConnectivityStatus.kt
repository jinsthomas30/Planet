package com.example.planet.common.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.planet.common.data.ConnectionState
import com.example.planet.common.data.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun connectivityStatus():Boolean {
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    return if (isConnected) {
        // Show UI when connectivity is available
        true
    } else {
        // Show UI for No Internet Connectivity
        false
    }
}