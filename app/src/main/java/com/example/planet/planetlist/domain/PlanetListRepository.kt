package com.example.planet.planetlist.domain

import com.example.planet.planetlist.data.PlanetResponse
import retrofit2.Response

interface PlanetListRepository {
    suspend fun fetchPlanetList(): Response<PlanetResponse>
}