package com.example.bus_api_client_xml.data.model

import com.google.gson.annotations.SerializedName

data class DriverResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    @SerializedName("assignedLine")
    val line: LineResponse
)