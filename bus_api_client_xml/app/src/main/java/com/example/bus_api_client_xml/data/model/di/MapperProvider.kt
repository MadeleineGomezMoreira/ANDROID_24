package com.example.bus_api_client_xml.data.model.di

import com.example.bus_api_client_xml.data.model.DataMappers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperProvider {

    @Singleton
    @Provides
    fun provideDataMappers(): DataMappers {
        return DataMappers()
    }

}