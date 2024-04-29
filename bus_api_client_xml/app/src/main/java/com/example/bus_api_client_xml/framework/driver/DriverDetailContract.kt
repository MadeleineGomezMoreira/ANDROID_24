package com.example.bus_api_client_xml.framework.driver

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.domain.model.BusDriver
import com.example.bus_api_client_xml.domain.model.BusLine

class DriverDetailContract {

    data class DriverDetailState(
        val busDriver: BusDriver = BusDriver(
            0,
            Constants.EMPTY_LITERAL_STRING,
            Constants.EMPTY_LITERAL_STRING,
            Constants.EMPTY_LITERAL_STRING,
            BusLine(0, Constants.EMPTY_LITERAL_STRING, Constants.EMPTY_LITERAL_STRING)
        ),
        val loading: Boolean = false,
        val error: String? = null,
    )

    sealed class DriverDetailEvent {
        class GetDriver(val driverId: Int) : DriverDetailEvent()
        data object ErrorDisplayed : DriverDetailEvent()
    }
}