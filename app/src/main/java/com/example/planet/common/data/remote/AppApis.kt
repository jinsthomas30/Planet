package com.example.planet.common.data.remote

import com.example.planet.planetdetails.data.PlanetDetailsResponse
import com.example.planet.planetlist.data.PlanetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApis {
    @GET("planets?page=1&limit=60")
    suspend fun fetchPlanetList(): Response<PlanetResponse>

    @GET("planets/{id}")
    suspend fun fetchPlanetDetails(@Path("id") uid: String): Response<PlanetDetailsResponse>
}