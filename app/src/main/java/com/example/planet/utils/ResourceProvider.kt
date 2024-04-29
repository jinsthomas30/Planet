package com.example.planet.utils

interface ResourceProvider {
    fun getString(resId: Int): String
}
