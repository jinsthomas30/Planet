package com.example.planet.respository

import com.example.planet.local.dao.PlanetDao
import com.example.planet.ui.planetdetails.data.PlanetDtEntity
import com.example.planet.ui.planetlist.data.PlanetEntity
import javax.inject.Inject

class PlanetDbRepository @Inject constructor (private val planetDao: PlanetDao) {
    suspend fun getAllPlanet(): List<PlanetEntity> {
        return planetDao.getAllPlanet()
    }

    suspend fun insertPlanetList(planet: List<PlanetEntity>) {
        return planetDao.insertPlanetList(planet)
    }

    suspend fun getPlanetDt(uid:String): PlanetDtEntity {
        return planetDao.getPlanetDt(uid)
    }

    suspend fun insertPlanetDt(planetDt: PlanetDtEntity) {
        return planetDao.insertPlanetDt(planetDt)
    }

}