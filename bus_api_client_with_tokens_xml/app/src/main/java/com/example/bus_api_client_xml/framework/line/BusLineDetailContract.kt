package com.example.bus_api_client_xml.framework.line

import com.example.bus_api_client_xml.domain.model.BusLine
import com.example.bus_api_client_xml.domain.model.BusStop
import com.example.bus_api_client_xml.utils.Constants

class BusLineDetailContract {

    data class BusLineDetailState(
        val busLine: BusLine = BusLine(
            0,
            Constants.EMPTY_LITERAL_STRING,
            Constants.EMPTY_LITERAL_STRING
        ),
        val stops: List<BusStop> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null,
    )

    sealed class BusLineDetailEvent {
        class GetBusLine(val lineId: Int) : BusLineDetailEvent()
        class GetBusLineStops(val lineId: Int) : BusLineDetailEvent()
        data object StopsDisplayed : BusLineDetailEvent()
        data object ErrorDisplayed : BusLineDetailEvent()
    }
}