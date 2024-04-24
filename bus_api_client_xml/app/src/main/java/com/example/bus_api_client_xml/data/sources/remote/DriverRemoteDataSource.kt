package com.example.bus_api_client_xml.data.sources.remote

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.DataMappers
import com.example.bus_api_client_xml.data.sources.remote.services.DriverService
import com.example.bus_api_client_xml.domain.model.BusDriver
import com.example.bus_api_client_xml.utils.NetworkResult
import javax.inject.Inject

class DriverRemoteDataSource @Inject constructor(
    private val driverService: DriverService,
    private val dm: DataMappers
) {
    suspend fun getAllDrivers(): NetworkResult<List<BusDriver>> {
        return try {
            val response = driverService.getAll()
            if (response.isSuccessful) {
                val drivers = response.body()?.map { driverResponse ->
                    dm.run {
                        driverResponse.toBusDriver()
                    }
                } ?: emptyList()
                if(drivers.isEmpty()) return NetworkResult.Error(Constants.NO_DRIVERS_FOUND_ERROR)
                NetworkResult.Success(drivers)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getDriverById(id: Int): NetworkResult<BusDriver> {
        return try {
            val response = driverService.getById(id)
            if (response.isSuccessful) {
                response.body()?.let { driverResponse ->
                    NetworkResult.Success(dm.run {
                        driverResponse.toBusDriver()
                    })
                } ?: NetworkResult.Error(Constants.NO_DRIVER_FOUND_ERROR)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}