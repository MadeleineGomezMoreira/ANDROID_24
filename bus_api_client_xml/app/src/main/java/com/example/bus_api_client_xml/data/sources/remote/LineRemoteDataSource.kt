package com.example.bus_api_client_xml.data.sources.remote

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.DataMappers
import com.example.bus_api_client_xml.data.sources.remote.services.LineService
import com.example.bus_api_client_xml.domain.model.BusLine
import com.example.bus_api_client_xml.utils.NetworkResult
import javax.inject.Inject

class LineRemoteDataSource @Inject constructor(
    private val lineService: LineService,
    private val dm : DataMappers
) {
    suspend fun getAllLines() : NetworkResult<List<BusLine>> {
        return try {
            val response = lineService.getAll()
            if (response.isSuccessful) {
                val lines = response.body()?.map { lineResponse ->
                    dm.run {
                        lineResponse.toBusLine()
                    }
                } ?: emptyList()
                if(lines.isEmpty()) return NetworkResult.Error(Constants.NO_LINES_FOUND_ERROR)
                NetworkResult.Success(lines)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getLineById(id: Int): NetworkResult<BusLine> {
        return try {
            val response = lineService.getById(id)
            if (response.isSuccessful) {
                response.body()?.let { lineResponse ->
                    NetworkResult.Success(dm.run {
                        lineResponse.toBusLine()
                    })
                } ?: NetworkResult.Error(Constants.NO_LINE_FOUND_ERROR)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getLinesInAStop(id: Int): NetworkResult<List<BusLine>> {
        return try {
            val response = lineService.getLinesInAStop(id)
            if (response.isSuccessful) {
                val lines = response.body()?.map { lineResponse ->
                    dm.run {
                        lineResponse.toBusLine()
                    }
                } ?: emptyList()
                if(lines.isEmpty()) return NetworkResult.Error(Constants.NO_LINES_FOUND_ERROR)
                NetworkResult.Success(lines)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}