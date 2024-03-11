package com.example.planet.network

import com.example.planet.ui.planetdetails.data.PlanetDetailsResponse
import com.example.planet.ui.planetlist.data.PlanetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApis {
    @GET("planets?page=1&limit=60")
    suspend fun fetchPlanetList(): Response<PlanetResponse>

    @GET("planets/{id}")
    suspend fun fetchPlanetDetails(@Path("id") uid: String): Response<PlanetDetailsResponse>
}