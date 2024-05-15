package com.example.bus_api_client_xml.framework.stop

import com.example.bus_api_client_xml.domain.model.BusLine
import com.example.bus_api_client_xml.domain.model.BusStop
import com.example.bus_api_client_xml.domain.model.LocationPoint
import com.example.bus_api_client_xml.utils.Constants

class StopDetailContract {

    data class StopDetailState(
        val busStop: BusStop = BusStop(0, Constants.EMPTY_LITERAL_STRING, LocationPoint(0.0, 0.0)),
        val lines: List<BusLine> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null,
    )

    sealed class StopDetailEvent {
        class GetStop(val stopId: Int) : StopDetailEvent()
        class GetStopLines(val stopId: Int) : StopDetailEvent()
        data object LinesDisplayed : StopDetailEvent()
        data object ErrorDisplayed : StopDetailEvent()
    }
}