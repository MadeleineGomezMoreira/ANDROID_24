package com.example.bus_api_client_xml.data.sources.remote

import com.example.bus_api_client_xml.data.model.LoginDTO
import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.data.sources.remote.services.AuthService
import com.example.bus_api_client_xml.utils.NetworkResult
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {

    suspend fun login(loginDTO: LoginDTO): NetworkResult<Unit> {
        return try {
            val response = authService.login(loginDTO)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun register(registerDTO: RegisterDTO) : NetworkResult<Unit> {
        return try {
            val response = authService.register(registerDTO)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }



}