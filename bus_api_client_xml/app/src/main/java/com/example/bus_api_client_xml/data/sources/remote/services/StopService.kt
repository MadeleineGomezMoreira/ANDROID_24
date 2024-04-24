package com.example.bus_api_client_xml.data.sources.remote.services

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.StopResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StopService {

    @GET(Constants.STOPS_PATH)
    suspend fun getAll(): Response<List<StopResponse>>

    @GET(Constants.STOP_BY_ID_PATH)
    suspend fun getById(@Path(Constants.ID_PARAM) id: Int): Response<StopResponse>

    @GET(Constants.STOPS_IN_A_LINE_PATH)
    suspend fun getStopsInALine(@Path(Constants.ID_PARAM) id: Int): Response<List<StopResponse>>
}