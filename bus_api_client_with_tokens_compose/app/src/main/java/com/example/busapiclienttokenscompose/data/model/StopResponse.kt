package com.example.busapiclienttokenscompose.data.model

import com.example.busapiclienttokenscompose.domain.model.LocationPoint

data class StopResponse(
    val id: Int,
    val name: String,
    val location: LocationPoint
)