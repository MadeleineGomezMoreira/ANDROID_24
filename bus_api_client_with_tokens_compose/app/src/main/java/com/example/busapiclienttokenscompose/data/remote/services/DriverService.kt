package com.example.busapiclienttokenscompose.data.remote.services

import com.example.busapiclienttokenscompose.utils.Constants
import com.example.busapiclienttokenscompose.data.model.DriverResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DriverService {

    @GET(Constants.DRIVERS_PATH)
    suspend fun getAll(): Response<List<DriverResponse>>

    @GET(Constants.DRIVER_BY_ID_PATH)
    suspend fun getById(@Path(Constants.ID_PARAM) id: Int): Response<DriverResponse>

    @GET(Constants.GET_DRIVER_ID_BY_USERNAME_PATH)
    suspend fun getIdByUsername(@Path(Constants.USERNAME_PARAM) username: String): Response<Int>
}