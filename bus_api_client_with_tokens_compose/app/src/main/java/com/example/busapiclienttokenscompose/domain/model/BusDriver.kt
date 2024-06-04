package com.example.busapiclienttokenscompose.domain.model

data class BusDriver (
    val id : Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val line: BusLine
)