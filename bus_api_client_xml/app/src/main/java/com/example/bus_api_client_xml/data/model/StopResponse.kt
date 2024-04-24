package com.example.bus_api_client_xml.data.model

import android.graphics.Point

data class StopResponse (
    val id: Int,
    val name: String,
    val location: Point
)