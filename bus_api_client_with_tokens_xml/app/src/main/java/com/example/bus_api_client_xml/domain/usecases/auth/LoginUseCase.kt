package com.example.bus_api_client_xml.domain.usecases.auth

import com.example.bus_api_client_xml.data.model.LoginDTO
import com.example.bus_api_client_xml.data.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke(loginDTO: LoginDTO) = repository.login(loginDTO)
}