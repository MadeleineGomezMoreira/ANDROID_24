package com.example.busapiclienttokenscompose.data.model

import com.example.busapiclienttokenscompose.domain.model.BusDriver
import com.example.busapiclienttokenscompose.domain.model.BusLine
import com.example.busapiclienttokenscompose.domain.model.BusStop

fun StopResponse.toBusStop(): BusStop {
        return BusStop(
            id = id,
            name = name,
            location = location
        )
    }

    fun LineResponse.toBusLine(): BusLine {
        return BusLine(
            id = id,
            lineEnd = lineEnd,
            lineStart = lineStart
        )
    }

    fun DriverResponse.toBusDriver(): BusDriver {
        return BusDriver(
            id = id,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            line = line.toBusLine()
        )
    }