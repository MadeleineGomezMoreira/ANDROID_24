package com.example.bus_api_client_xml.domain.usecases.auth

import com.example.bus_api_client_xml.data.repositories.AuthRepository
import javax.inject.Inject

class LogOffUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke() = repository.logOff()
}