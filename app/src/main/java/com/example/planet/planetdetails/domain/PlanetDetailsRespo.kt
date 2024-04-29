package com.example.planet.planetdetails.domain

import com.example.planet.planetdetails.data.PlanetDetailsResponse
import retrofit2.Response

interface PlanetDetailsRespo {
    suspend fun fetchPlanetDetails(uid:String): Response<PlanetDetailsResponse>
}