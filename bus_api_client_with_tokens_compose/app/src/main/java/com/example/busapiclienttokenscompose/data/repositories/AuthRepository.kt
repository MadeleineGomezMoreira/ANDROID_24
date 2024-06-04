package com.example.busapiclienttokenscompose.data.repositories

import com.example.busapiclienttokenscompose.data.remote.AuthRemoteDataSource
import com.example.bus_api_client_xml.utils.NetworkResult
import com.example.busapiclienttokenscompose.domain.model.LoginDTO
import com.example.busapiclienttokenscompose.domain.model.RegisterDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    fun login(loginDTO: LoginDTO): Flow<NetworkResult<Int>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = authRemoteDataSource.login(loginDTO)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun register(registerDTO: RegisterDTO): Flow<NetworkResult<Unit>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = authRemoteDataSource.register(registerDTO)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun logOff(): Flow<NetworkResult<Unit>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = authRemoteDataSource.logOff()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}