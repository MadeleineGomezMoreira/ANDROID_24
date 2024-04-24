package com.example.bus_api_client_xml.domain.usecases.auth

import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.data.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    //TODO: implement validation error control
    operator fun invoke(registerDTO: RegisterDTO) = repository.register(registerDTO)
}