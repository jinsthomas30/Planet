package com.example.planet.planetdetails.domain

import com.example.planet.common.data.remote.AppApis
import com.example.planet.planetdetails.data.PlanetDetailsResponse
import retrofit2.Response

class PlanetDetailsRespoImplementation(private val api: AppApis):PlanetDetailsRespo {
    override suspend fun fetchPlanetDetails(uid: String): Response<PlanetDetailsResponse> {
        return api.fetchPlanetDetails(uid)
    }
}