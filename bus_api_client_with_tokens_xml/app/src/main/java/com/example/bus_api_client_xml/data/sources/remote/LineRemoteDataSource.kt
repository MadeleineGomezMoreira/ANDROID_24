package com.example.bus_api_client_xml.data.sources.remote

import com.example.bus_api_client_xml.utils.Constants
import com.example.bus_api_client_xml.data.model.toBusLine
import com.example.bus_api_client_xml.data.sources.remote.services.LineService
import com.example.bus_api_client_xml.domain.model.BusLine
import com.example.bus_api_client_xml.utils.NetworkResult
import timber.log.Timber
import javax.inject.Inject

class LineRemoteDataSource @Inject constructor(
    private val lineService: LineService,
) {
    suspend fun getAllLines() : NetworkResult<List<BusLine>> {
        return try {
            val response = lineService.getAll()
            if (response.isSuccessful) {
                val lines = response.body()?.map { lineResponse ->
                        lineResponse.toBusLine()
                } ?: emptyList()
                if(lines.isEmpty()) return NetworkResult.Error(Constants.NO_LINES_FOUND_ERROR)
                NetworkResult.Success(lines)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_ALL_LINES_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_ALL_LINES_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getLineById(id: Int): NetworkResult<BusLine> {
        return try {
            val response = lineService.getById(id)
            if (response.isSuccessful) {
                response.body()?.let { lineResponse ->
                    NetworkResult.Success(
                        lineResponse.toBusLine()
                    )
                } ?: NetworkResult.Error(Constants.NO_LINE_FOUND_ERROR)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_LINE_BY_ID_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_LINE_BY_ID_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getLinesInAStop(id: Int): NetworkResult<List<BusLine>> {
        return try {
            val response = lineService.getLinesInAStop(id)
            if (response.isSuccessful) {
                val lines = response.body()?.map { lineResponse ->
                        lineResponse.toBusLine()
                } ?: emptyList()
                if(lines.isEmpty()) return NetworkResult.Error(Constants.NO_LINES_FOUND_ERROR)
                NetworkResult.Success(lines)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_LINES_IN_STOP_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_LINES_IN_STOP_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}