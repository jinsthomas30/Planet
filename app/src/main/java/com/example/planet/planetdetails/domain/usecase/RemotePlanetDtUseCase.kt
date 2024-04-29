package com.example.planet.planetdetails.domain.usecase

import com.example.planet.planetdetails.data.PlanetDetailsResponse
import com.example.planet.planetdetails.domain.PlanetDetailsRespo
import com.vivek.githubapisample.common.domain.AsyncUsecase
import retrofit2.Response
import javax.inject.Inject

class RemotePlanetDtUseCase @Inject constructor(private val repository: PlanetDetailsRespo) :
    AsyncUsecase<RemotePlanetDtUseCase.Param, Response<PlanetDetailsResponse>> {

    data class Param(val id:String)

    override suspend fun invoke(params: Param): Response<PlanetDetailsResponse> {
        return repository.fetchPlanetDetails(params.id)
    }
}