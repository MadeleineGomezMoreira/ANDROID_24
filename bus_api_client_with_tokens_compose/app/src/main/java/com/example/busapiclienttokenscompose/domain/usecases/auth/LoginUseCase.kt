package com.example.busapiclienttokenscompose.domain.usecases.auth

import com.example.busapiclienttokenscompose.data.repositories.AuthRepository
import com.example.busapiclienttokenscompose.domain.model.LoginDTO
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke(loginDTO: LoginDTO) = repository.login(loginDTO)
}