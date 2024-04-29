package com.example.planet.planetdetails.domain.usecase

import com.example.planet.common.domain.PlanetDbRepository
import com.example.planet.planetdetails.data.PlanetDtEntity
import com.vivek.githubapisample.common.domain.AsyncUsecase
import javax.inject.Inject

class LocalPlanetDtLocalUseCase @Inject constructor(private val repository: PlanetDbRepository) :
    AsyncUsecase<LocalPlanetDtLocalUseCase.Param, PlanetDtEntity?> {

    data class Param(val id:String)

    override suspend fun invoke(params: Param): PlanetDtEntity? {
        return repository.getPlanetDetailsByUid(params.id)
    }
}