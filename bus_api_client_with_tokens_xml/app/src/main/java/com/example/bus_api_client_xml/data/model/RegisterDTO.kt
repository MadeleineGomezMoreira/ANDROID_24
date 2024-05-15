package com.example.bus_api_client_xml.data.model

data class RegisterDTO(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val username: String,
    val password: String,
    val email: String,
)