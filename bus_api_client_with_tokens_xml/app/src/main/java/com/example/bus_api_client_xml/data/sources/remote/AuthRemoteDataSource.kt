package com.example.bus_api_client_xml.data.sources.remote

import com.example.bus_api_client_xml.data.model.LoginDTO
import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.data.sources.remote.di.TokenManager
import com.example.bus_api_client_xml.data.sources.remote.services.AuthService
import com.example.bus_api_client_xml.utils.Constants
import com.example.bus_api_client_xml.utils.NetworkResult
import timber.log.Timber
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService,
    private val tokenManager: TokenManager,
) {

    suspend fun login(loginDTO: LoginDTO): NetworkResult<Int> {
        return try {
            val response = authService.login(loginDTO)
            if (response.isSuccessful) {
                var id = -1
                response.body()?.let {
                    tokenManager.saveAccessToken(it.tokenPair.accessToken)
                    tokenManager.saveRefreshToken(it.tokenPair.refreshToken)
                    id = it.userId
                }
                if (id == -1) return NetworkResult.Error(Constants.LOGIN_ERROR)
                NetworkResult.Success(id)
            } else {
                Timber.i(response.message(), Constants.LOGIN_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.LOGIN_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun register(registerDTO: RegisterDTO): NetworkResult<Unit> {
        return try {
            val response = authService.register(registerDTO)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                Timber.i(response.message(), Constants.REGISTRATION_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.REGISTRATION_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun logOff(): NetworkResult<Unit> {
        return try {
            val response = authService.logOff()
            if (response.isSuccessful) {
                tokenManager.deleteTokens()
                NetworkResult.Success(Unit)
            } else {
                Timber.i(response.message(), Constants.LOGOFF_ERROR)
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e, Constants.LOGOFF_ERROR)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }


}