package com.example.planet.ui.planetdetails.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PlanetDetailsResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: Result?
)

data class Result(
    @SerializedName("__v")
    val __v: Int?,
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("properties")
    val properties: PlanetDtEntity?,
    @SerializedName("uid")
    val uid: String?
)

@Entity(tableName = "planetDtEntity")
data class PlanetDtEntity(
    @PrimaryKey
    @SerializedName("uid")
    var uid: String,
    @SerializedName("climate")
    val climate: String?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("diameter")
    val diameter: String?,
    @SerializedName("edited")
    val edited: String?,
    @SerializedName("gravity")
    val gravity: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("orbital_period")
    val orbital_period: String?,
    @SerializedName("population")
    val population: String?,
    @SerializedName("rotation_period")
    val rotation_period: String?,
    @SerializedName("surface_water")
    val surface_water: String?,
    @SerializedName("terrain")
    val terrain: String?,
    @SerializedName("url")
    val url: String?
)