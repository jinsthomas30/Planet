package com.example.planet.respository

import com.example.planet.ui.planetdetails.data.PlanetDetailsResponse
import com.example.planet.ui.planetlist.data.PlanetResponse
import retrofit2.Response

interface MyApiRepository {
    suspend fun doNetworkCal(): Response<String>
    suspend fun fetchPlanetList(): Response<PlanetResponse>
    suspend fun fetchPlanetDetails(uid:String): Response<PlanetDetailsResponse>

}