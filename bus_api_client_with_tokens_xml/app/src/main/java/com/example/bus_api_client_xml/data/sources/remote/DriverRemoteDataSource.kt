package com.example.bus_api_client_xml.data.sources.remote

import com.example.bus_api_client_xml.data.model.toBusDriver
import com.example.bus_api_client_xml.data.sources.remote.services.DriverService
import com.example.bus_api_client_xml.domain.model.BusDriver
import com.example.bus_api_client_xml.utils.Constants
import com.example.bus_api_client_xml.utils.NetworkResult
import timber.log.Timber
import javax.inject.Inject

class DriverRemoteDataSource @Inject constructor(
    private val driverService: DriverService,
) {
    suspend fun getAllDrivers(): NetworkResult<List<BusDriver>> {
        return try {
            val response = driverService.getAll()
            if (response.isSuccessful) {
                val drivers = response.body()?.map { driverResponse ->
                        driverResponse.toBusDriver()
                } ?: emptyList()
                if (drivers.isEmpty()) return NetworkResult.Error(Constants.NO_DRIVERS_FOUND_ERROR)
                NetworkResult.Success(drivers)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_ALL_DRIVERS_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_ALL_DRIVERS_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getDriverById(id: Int): NetworkResult<BusDriver> {
        return try {
            val response = driverService.getById(id)
            if (response.isSuccessful) {
                response.body()?.let { driverResponse ->
                    NetworkResult.Success(
                        driverResponse.toBusDriver()
                    )
                } ?: NetworkResult.Error(Constants.NO_DRIVER_FOUND_ERROR)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_DRIVER_BY_ID_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_DRIVER_BY_ID_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getDriverIdByUsername(username: String): NetworkResult<Int> {
        return try {
            val response = driverService.getIdByUsername(username)
            if (response.isSuccessful) {
                response.body()?.let { id ->
                    NetworkResult.Success(id)
                } ?: NetworkResult.Error(Constants.NO_DRIVER_FOUND_ERROR)
            } else {
                Timber.i(response.message(), Constants.RETRIEVING_DRIVER_ID_BY_USERNAME_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.RETRIEVING_DRIVER_ID_BY_USERNAME_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}