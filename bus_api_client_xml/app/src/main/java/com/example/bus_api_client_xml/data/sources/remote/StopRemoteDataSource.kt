package com.example.bus_api_client_xml.data.sources.remote

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.DataMappers
import com.example.bus_api_client_xml.data.sources.remote.services.StopService
import com.example.bus_api_client_xml.domain.model.BusStop
import com.example.bus_api_client_xml.utils.NetworkResult
import javax.inject.Inject

class StopRemoteDataSource @Inject constructor(
    private val stopService: StopService,
    private val dm : DataMappers
) {

    suspend fun getAllStops() : NetworkResult<List<BusStop>> {
        return try {
            val response = stopService.getAll()
            if(response.isSuccessful) {
                val stops = response.body()?.map { stopResponse ->
                    dm.run {
                        stopResponse.toBusStop()
                    }
                } ?: emptyList()
                if(stops.isEmpty()) return NetworkResult.Error(Constants.NO_STOPS_FOUND_ERROR)
                NetworkResult.Success(stops)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getStopById(id: Int) : NetworkResult<BusStop> {
        return try {
            val response = stopService.getById(id)
            if(response.isSuccessful) {
                response.body()?.let { stopResponse ->
                    NetworkResult.Success(dm.run {
                        stopResponse.toBusStop()
                    })
                } ?: NetworkResult.Error(Constants.NO_STOP_FOUND_ERROR)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getStopsInALine(id: Int) : NetworkResult<List<BusStop>> {
        return try {
            val response = stopService.getStopsInALine(id)
            if(response.isSuccessful) {
                val stops = response.body()?.map { stopResponse ->
                    dm.run {
                        stopResponse.toBusStop()
                    }
                } ?: emptyList()
                if(stops.isEmpty()) return NetworkResult.Error(Constants.NO_STOPS_FOUND_ERROR)
                NetworkResult.Success(stops)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

}