package com.example.bus_api_client_xml.data.sources.remote.services

import com.example.bus_api_client_xml.utils.Constants
import com.example.bus_api_client_xml.data.model.LoginDTO
import com.example.bus_api_client_xml.data.model.auth.RefreshToken
import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.data.model.auth.LoginData
import com.example.bus_api_client_xml.data.model.auth.TokenPair
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST(Constants.LOGIN_PATH)
    suspend fun login(@Body loginDTO: LoginDTO): Response<LoginData>

    @POST(Constants.REGISTER_PATH)
    suspend fun register(@Body registerDTO: RegisterDTO): Response<Unit>

    @POST(Constants.REFRESH_TOKEN_PATH)
    suspend fun refreshToken(@Body refreshToken: RefreshToken): Response<TokenPair>

    @POST(Constants.LOGOFF_PATH)
    suspend fun logOff(): Response<Unit>
}