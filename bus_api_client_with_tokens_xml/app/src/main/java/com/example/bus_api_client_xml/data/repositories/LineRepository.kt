package com.example.bus_api_client_xml.data.repositories

import com.example.bus_api_client_xml.data.sources.remote.LineRemoteDataSource
import com.example.bus_api_client_xml.domain.model.BusLine
import javax.inject.Inject
import com.example.bus_api_client_xml.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LineRepository @Inject constructor(
    private val lineRemoteDataSource: LineRemoteDataSource
){

    fun getAllLines(): Flow<NetworkResult<List<BusLine>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = lineRemoteDataSource.getAllLines()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getLineById(id: Int): Flow<NetworkResult<BusLine>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = lineRemoteDataSource.getLineById(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getLinesInAStop(id: Int): Flow<NetworkResult<List<BusLine>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = lineRemoteDataSource.getLinesInAStop(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}