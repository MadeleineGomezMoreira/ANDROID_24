package com.example.bus_api_client_xml.domain.model

data class BusDriver (
    val firstName: String,
    val lastName: String,
    val phone: String,
    val line: BusLine
)