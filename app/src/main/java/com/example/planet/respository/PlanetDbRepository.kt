package com.example.planet.respository

import com.example.planet.local.dao.PlanetDao
import com.example.planet.ui.planetlist.data.PlanetEntity
import javax.inject.Inject

class PlanetDbRepository @Inject constructor (private val planetDao: PlanetDao) {
    suspend fun getAllTask(): List<PlanetEntity> {
        return planetDao.getAllPlanet()
    }

    suspend fun insertTask(planet: List<PlanetEntity>) {
        return planetDao.insertPlanet(planet)
    }

    suspend fun deleteTask(planet: PlanetEntity) {
        return planetDao.deletePlanet(planet)
    }
}