package com.example.planet.planetlist.data.di

import com.example.planet.common.data.remote.AppApis
import com.example.planet.planetlist.domain.PlanetListRepository
import com.example.planet.planetlist.domain.PlanetListRespoRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PlanetListService {

    @Provides
    @Singleton
    fun providePlanetListRemoteRepository(api : AppApis) : PlanetListRepository {
        return PlanetListRespoRemote(api)
    }

}