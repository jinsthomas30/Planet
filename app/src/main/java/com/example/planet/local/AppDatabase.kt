package com.example.planet.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.planet.local.dao.PlanetDao
import com.example.planet.ui.planetlist.data.PlanetItem

@Database(entities = [PlanetItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun planetDao(): PlanetDao

}