package com.example.planet.planetlist.domain.usecase

import com.example.planet.common.domain.PlanetDbRepository
import com.example.planet.planetlist.data.PlanetEntity
import com.vivek.githubapisample.common.domain.AsyncUsecase
import javax.inject.Inject

class InsertPlanetListUseCase  @Inject constructor(private val repository: PlanetDbRepository) :
    AsyncUsecase<InsertPlanetListUseCase.Param, List<Long>> {

    data class Param(val results: List<PlanetEntity>)

    override suspend fun invoke(params: Param): List<Long> {
        return repository.insertPlanetList(params.results)
    }
}