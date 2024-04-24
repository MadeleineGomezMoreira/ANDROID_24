package com.example.bus_api_client_xml.domain.model

import android.graphics.Point

data class BusStop (
    val id: Int,
    val name: String,
    val location: Point
)