package com.example.planet.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.planet.local.dao.PlanetDao
import com.example.planet.ui.planetdetails.data.PlanetDtEntity
import com.example.planet.ui.planetlist.data.PlanetEntity

@Database(
    entities = [PlanetEntity::class, PlanetDtEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun planetDao(): PlanetDao

}