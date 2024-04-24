package com.example.bus_api_client_xml.data.repositories

import com.example.bus_api_client_xml.data.sources.remote.DriverRemoteDataSource
import com.example.bus_api_client_xml.domain.model.BusDriver
import com.example.bus_api_client_xml.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DriverRepository @Inject constructor(
    private val driverRemoteDataSource: DriverRemoteDataSource
) {

    fun getAllDrivers(): Flow<NetworkResult<List<BusDriver>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = driverRemoteDataSource.getAllDrivers()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getDriverById(id: Int): Flow<NetworkResult<BusDriver>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = driverRemoteDataSource.getDriverById(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}