package com.example.planet.ui.planetlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

class PlanetItems : ArrayList<PlanetItem>()

@Entity(tableName = "planet")
data class PlanetItem(
    @PrimaryKey()
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String
)

data class Rating(
    val count: Int,
    val rate: Double
)