package com.example.planet.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.planet.ui.planetlist.data.PlanetItem
@Dao
interface PlanetDao {
    @Query("SELECT * FROM planet")
    suspend fun getAllPlanet() : List<PlanetItem>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet : List<PlanetItem>)
    @Delete
    suspend fun deletePlanet(planet : PlanetItem)
}