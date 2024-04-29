package com.example.bus_api_client_xml.data.model

import com.example.bus_api_client_xml.domain.model.BusDriver
import com.example.bus_api_client_xml.domain.model.BusLine
import com.example.bus_api_client_xml.domain.model.BusStop
import javax.inject.Inject

class DataMappers @Inject constructor() {

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

}