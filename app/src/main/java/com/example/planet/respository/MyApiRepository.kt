package com.example.planet.respository

import com.example.planet.ui.planetdetails.data.PlanetDetailsResponse
import com.example.planet.ui.planetlist.data.PlanetResponse
import retrofit2.Response

interface MyApiRepository {
    suspend fun doNetworkCal(): Response<String>
    suspend fun planetList(): Response<PlanetResponse>
    suspend fun planetDetails(uid:String): Response<PlanetDetailsResponse>

}