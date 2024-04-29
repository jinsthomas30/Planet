package com.example.planet.planetdetails.domain.usecase

import com.example.planet.common.domain.PlanetDbRepository
import com.example.planet.planetdetails.data.PlanetDtEntity
import com.vivek.githubapisample.common.domain.AsyncUsecase
import javax.inject.Inject

class InsertPlanetDtUseCase  @Inject constructor(private val repository: PlanetDbRepository) :
    AsyncUsecase<InsertPlanetDtUseCase.Param, Long> {

    data class Param(val planetDtEntity: PlanetDtEntity)

    override suspend fun invoke(params: Param): Long {
        return repository.insertPlanetDetailsByUid(params.planetDtEntity)
    }
}