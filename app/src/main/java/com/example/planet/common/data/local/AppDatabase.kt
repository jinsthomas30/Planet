package com.example.planet.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.planet.common.data.PlanetDao
import com.example.planet.planetdetails.data.PlanetDtEntity
import com.example.planet.planetlist.data.PlanetEntity

@Database(
    entities = [PlanetEntity::class, PlanetDtEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun planetDao(): PlanetDao

}