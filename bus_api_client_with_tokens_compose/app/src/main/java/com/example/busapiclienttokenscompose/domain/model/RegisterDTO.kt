package com.example.busapiclienttokenscompose.domain.model

data class RegisterDTO(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val username: String = "",
    val password: String = "",
    val email: String = "",
)

