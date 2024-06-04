package com.example.busapiclienttokenscompose.data.remote.services

import com.example.busapiclienttokenscompose.utils.Constants
import com.example.busapiclienttokenscompose.data.model.auth.RefreshToken
import com.example.busapiclienttokenscompose.data.model.auth.LoginData
import com.example.busapiclienttokenscompose.data.model.auth.TokenPair
import com.example.busapiclienttokenscompose.domain.model.LoginDTO
import com.example.busapiclienttokenscompose.domain.model.RegisterDTO
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