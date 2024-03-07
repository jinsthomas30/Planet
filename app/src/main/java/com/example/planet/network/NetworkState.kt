package com.example.planet.network

sealed class NetworkState {
    object None: NetworkState()
    object Connected: NetworkState()
    object NotConnected: NetworkState()
}