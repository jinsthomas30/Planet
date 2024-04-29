package com.example.planet.common.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.planet.planetdetails.data.PlanetDtEntity
import com.example.planet.planetlist.data.PlanetEntity

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planetEntity")
    suspend fun getAllPlanet(): List<PlanetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanetList(planet: List<PlanetEntity>):List<Long>

    @Query("SELECT * FROM planetDtEntity where uid =:uid")
    suspend fun getPlanetDt(uid:String): PlanetDtEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanetDt(planetDtEntity: PlanetDtEntity):Long
}