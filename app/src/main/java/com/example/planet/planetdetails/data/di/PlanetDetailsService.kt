package com.example.planet.planetdetails.data.di

import com.example.planet.common.data.remote.AppApis
import com.example.planet.planetdetails.domain.PlanetDetailsRespo
import com.example.planet.planetdetails.domain.PlanetDetailsRespoImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PlanetDetailsService {
    @Provides
    @Singleton
    fun providePlanetDetailsRespo(api : AppApis) : PlanetDetailsRespo {
        return PlanetDetailsRespoImplementation(api)
    }
}