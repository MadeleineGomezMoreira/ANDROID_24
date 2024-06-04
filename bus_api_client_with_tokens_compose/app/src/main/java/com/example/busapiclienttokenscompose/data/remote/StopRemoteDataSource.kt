package com.example.busapiclienttokenscompose.data.remote

import com.example.busapiclienttokenscompose.data.model.toBusStop
import com.example.busapiclienttokenscompose.data.remote.services.StopService
import com.example.busapiclienttokenscompose.domain.model.BusStop
import com.example.busapiclienttokenscompose.utils.Constants
import com.example.bus_api_client_xml.utils.NetworkResult
import timber.log.Timber
import javax.inject.Inject

class StopRemoteDataSource @Inject constructor(
    private val stopService: StopService,
) {

    suspend fun getAllStops(): NetworkResult<List<BusStop>> {
        return try {
            val response = stopService.getAll()
            if (response.isSuccessful) {
                val stops = response.body()?.map { stopResponse ->
                    stopResponse.toBusStop()
                } ?: emptyList()
                if (stops.isEmpty()) return NetworkResult.Error(Constants.NO_STOPS_FOUND_ERROR)
                NetworkResult.Success(stops)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_ALL_STOPS_ERROR)
                NetworkResult.Error(response.message())
            }
            //timber log to know which error we got and why
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_ALL_STOPS_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getStopById(id: Int): NetworkResult<BusStop> {
        return try {
            val response = stopService.getById(id)
            if (response.isSuccessful) {
                response.body()?.let { stopResponse ->
                    NetworkResult.Success(
                        stopResponse.toBusStop()
                    )
                } ?: NetworkResult.Error(Constants.NO_STOP_FOUND_ERROR)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_STOP_BY_ID_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_STOP_BY_ID_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getStopsInALine(id: Int): NetworkResult<List<BusStop>> {
        return try {
            val response = stopService.getStopsInALine(id)
            if (response.isSuccessful) {
                val stops = response.body()?.map { stopResponse ->
                    stopResponse.toBusStop()
                } ?: emptyList()
                if (stops.isEmpty()) return NetworkResult.Error(Constants.NO_STOPS_FOUND_ERROR)
                NetworkResult.Success(stops)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_STOPS_IN_LINE_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_STOPS_IN_LINE_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

}