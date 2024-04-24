package com.example.bus_api_client_xml.data.sources.remote.services

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.LoginDTO
import com.example.bus_api_client_xml.data.model.RegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST(Constants.LOGIN_PATH)
    suspend fun login(@Body loginDTO: LoginDTO): Response<Unit>

    @POST(Constants.REGISTER_PATH)
    suspend fun register(@Body registerDTO: RegisterDTO): Response<Unit>
}