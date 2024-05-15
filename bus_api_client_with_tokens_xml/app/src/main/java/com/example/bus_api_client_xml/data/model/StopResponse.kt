package com.example.bus_api_client_xml.data.model

import com.example.bus_api_client_xml.domain.model.LocationPoint

data class StopResponse(
    val id: Int,
    val name: String,
    val location: LocationPoint
)