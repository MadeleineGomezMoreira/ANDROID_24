package com.example.bus_api_client_xml.data.sources.remote.services

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.DriverResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DriverService {

    @GET(Constants.DRIVERS_PATH)
    suspend fun getAll(): Response<List<DriverResponse>>

    @GET(Constants.DRIVER_BY_ID_PATH)
    suspend fun getById(@Path(Constants.ID_PARAM) id: Int): Response<DriverResponse>
}