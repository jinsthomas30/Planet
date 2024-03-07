package com.example.planet.network

import com.example.planet.ui.planetlist.data.PlanetItem
import retrofit2.Response
import retrofit2.http.GET

interface AppApis {
    @GET("/products")
    suspend fun fetchPlanetList(): Response<List<PlanetItem>>
}