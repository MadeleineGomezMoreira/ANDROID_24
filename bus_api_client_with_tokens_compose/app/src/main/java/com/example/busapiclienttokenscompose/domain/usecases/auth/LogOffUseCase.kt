package com.example.busapiclienttokenscompose.domain.usecases.auth

import com.example.busapiclienttokenscompose.data.repositories.AuthRepository
import javax.inject.Inject

class LogOffUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke() = repository.logOff()
}