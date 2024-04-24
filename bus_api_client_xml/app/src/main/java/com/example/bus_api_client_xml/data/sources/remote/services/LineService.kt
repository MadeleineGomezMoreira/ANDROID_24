package com.example.bus_api_client_xml.data.sources.remote.services

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.LineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LineService {

    @GET(Constants.LINES_PATH)
    suspend fun getAll(): Response<List<LineResponse>>

    @GET(Constants.LINE_BY_ID_PATH)
    suspend fun getById(@Path(Constants.ID_PARAM) id: Int): Response<LineResponse>

    @GET(Constants.LINES_IN_A_STOP_PATH)
    suspend fun getLinesInAStop(@Path(Constants.ID_PARAM) id: Int): Response<List<LineResponse>>
}