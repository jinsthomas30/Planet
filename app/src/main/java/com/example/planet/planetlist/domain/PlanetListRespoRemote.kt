package com.example.planet.planetlist.domain

import com.example.planet.common.data.remote.AppApis
import com.example.planet.planetlist.data.PlanetResponse
import retrofit2.Response

class PlanetListRespoRemote(private val api: AppApis) : PlanetListRepository {
    override suspend fun fetchPlanetList(): Response<PlanetResponse> {
        return api.fetchPlanetList()
    }
}