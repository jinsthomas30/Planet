package com.example.planet.resource

interface ResourceProvider {
    fun getString(resId: Int): String
}
