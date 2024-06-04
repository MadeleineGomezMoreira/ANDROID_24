package com.example.busapiclienttokenscompose.domain.usecases.auth

import com.example.busapiclienttokenscompose.data.repositories.AuthRepository
import com.example.busapiclienttokenscompose.domain.model.RegisterDTO
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    //TODO: implement validation error control
    operator fun invoke(registerDTO: RegisterDTO) = repository.register(registerDTO)
}