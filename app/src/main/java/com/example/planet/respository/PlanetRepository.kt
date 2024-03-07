package com.example.planet.respository

import com.example.planet.local.dao.PlanetDao
import com.example.planet.ui.planetlist.data.PlanetItem
import javax.inject.Inject

class PlanetRepository @Inject constructor (private val planetDao: PlanetDao) {
    suspend fun getAllTask(): List<PlanetItem> {
        return planetDao.getAllPlanet()
    }

    suspend fun insertTask(planet: List<PlanetItem>) {
        return planetDao.insertPlanet(planet)
    }

    suspend fun deleteTask(planet: PlanetItem) {
        return planetDao.deletePlanet(planet)
    }
}