package com.example.planet.respository

import com.example.planet.network.AppApis
import com.example.planet.ui.planetdetails.data.PlanetDetailsResponse
import com.example.planet.ui.planetlist.data.PlanetResponse
import retrofit2.Response

class MyApiRepositoryImplementation(
    private val api: AppApis
) : MyApiRepository {
    override suspend fun doNetworkCal(): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun planetList(): Response<PlanetResponse> {
        return api.fetchPlanetList()
    }

    override suspend fun planetDetails(uid: String): Response<PlanetDetailsResponse> {
        return api.fetchPlanetDetails(uid)
    }


}