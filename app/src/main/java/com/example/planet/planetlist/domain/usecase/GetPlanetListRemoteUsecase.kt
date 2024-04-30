package com.example.planet.planetlist.domain.usecase

import com.example.planet.planetlist.data.PlanetResponse
import com.example.planet.planetlist.domain.PlanetListRepository
import com.example.planet.common.domain.AsyncUsecase
import retrofit2.Response
import javax.inject.Inject

class GetPlanetListRemoteUsecase @Inject constructor(private val repository: PlanetListRepository) :
    AsyncUsecase<GetPlanetListRemoteUsecase.Param, Response<PlanetResponse>> {

    data class Param(val empty:String)

    override suspend fun invoke(params: Param): Response<PlanetResponse> {
        return repository.fetchPlanetList()
    }
}