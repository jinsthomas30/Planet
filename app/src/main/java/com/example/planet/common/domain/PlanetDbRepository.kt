package com.example.planet.common.domain

import com.example.planet.common.data.PlanetDao
import com.example.planet.planetdetails.data.PlanetDtEntity
import com.example.planet.planetlist.data.PlanetEntity
import javax.inject.Inject

class PlanetDbRepository @Inject constructor (private val planetDao: PlanetDao) {
    suspend fun getAllPlanet(): List<PlanetEntity> {
        return planetDao.getAllPlanet()
    }

    suspend fun insertPlanetList(planet: List<PlanetEntity>):List<Long> {
        return planetDao.insertPlanetList(planet)
    }

    suspend fun getPlanetDetailsByUid(uid:String): PlanetDtEntity? {
        return planetDao.getPlanetDt(uid)
    }

    suspend fun insertPlanetDetailsByUid(planetDt: PlanetDtEntity):Long {
        return planetDao.insertPlanetDt(planetDt)
    }

}