package com.example.planet.respository

import com.example.planet.ui.planetlist.data.PlanetItem
import retrofit2.Response

interface MyRepository {
    suspend fun doNetworkCal() : Response<String>
    suspend fun planetList() : Response<List<PlanetItem>>

}