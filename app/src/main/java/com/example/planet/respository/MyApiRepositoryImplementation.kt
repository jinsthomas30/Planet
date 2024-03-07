package com.example.planet.respository

import com.example.planet.network.AppApis
import com.example.planet.ui.planetlist.data.PlanetItem
import retrofit2.Response

class MyApiRepositoryImplementation(
    private val api : AppApis
): MyApiRepository {
    override suspend fun doNetworkCal(): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun planetList(): Response<List<PlanetItem>> {
        return api.fetchPlanetList()
    }


}