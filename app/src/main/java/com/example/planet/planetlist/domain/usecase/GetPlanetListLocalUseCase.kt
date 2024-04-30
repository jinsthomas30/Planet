package com.example.planet.planetlist.domain.usecase

import com.example.planet.common.domain.PlanetDbRepository
import com.example.planet.planetlist.data.PlanetEntity
import com.example.planet.common.domain.AsyncUsecase
import javax.inject.Inject

class GetPlanetListLocalUseCase  @Inject constructor(private val repository: PlanetDbRepository) :
    AsyncUsecase<GetPlanetListLocalUseCase.Param, List<PlanetEntity>> {

    data class Param(val empty: String)

    override suspend fun invoke(params: Param): List<PlanetEntity> {
        return repository.getAllPlanet()
    }
}