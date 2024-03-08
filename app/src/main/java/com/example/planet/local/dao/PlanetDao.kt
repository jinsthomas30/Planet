package com.example.planet.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.planet.ui.planetlist.data.PlanetEntity

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planetEntity")
    suspend fun getAllPlanet() : List<PlanetEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet : List<PlanetEntity>)
    @Delete
    suspend fun deletePlanet(planet : PlanetEntity)
}