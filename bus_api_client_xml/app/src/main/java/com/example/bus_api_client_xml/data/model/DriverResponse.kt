package com.example.bus_api_client_xml.data.model

data class DriverResponse (
    val firstName: String,
    val lastName: String,
    val phone: String,
    val line: LineResponse
)