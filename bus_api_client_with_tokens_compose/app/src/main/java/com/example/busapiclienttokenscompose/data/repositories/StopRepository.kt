package com.example.busapiclienttokenscompose.data.repositories

import com.example.busapiclienttokenscompose.data.remote.StopRemoteDataSource
import com.example.busapiclienttokenscompose.domain.model.BusStop
import com.example.bus_api_client_xml.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StopRepository @Inject constructor(
    private val stopRemoteDataSource: StopRemoteDataSource
) {

    fun getAllStops(): Flow<NetworkResult<List<BusStop>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = stopRemoteDataSource.getAllStops()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getStopById(id: Int): Flow<NetworkResult<BusStop>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = stopRemoteDataSource.getStopById(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getStopsInALine(id: Int): Flow<NetworkResult<List<BusStop>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = stopRemoteDataSource.getStopsInALine(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}