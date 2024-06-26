package com.example.planet.common.data.di

import android.content.Context
import androidx.room.Room
import com.example.planet.common.data.local.AppDatabase
import com.example.planet.common.data.PlanetDao
import com.example.planet.common.data.remote.AppApis
import com.example.planet.utils.AndroidResourceProvider
import com.example.planet.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL =  "https://www.swapi.tech/api/"
    @Provides
    @Singleton //marks the scope of the dependency. i.e will have the single instance through out
    fun provideMyApi(): AppApis {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()



        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(AppApis::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase) : PlanetDao {
        return appDatabase.planetDao()
    }

    @Provides
    fun provideResourceProvider(@ApplicationContext appContext: Context): ResourceProvider {
        return AndroidResourceProvider(appContext)
    }



}
