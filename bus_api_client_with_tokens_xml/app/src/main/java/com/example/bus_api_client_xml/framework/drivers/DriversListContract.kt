package com.example.bus_api_client_xml.framework.drivers

import com.example.bus_api_client_xml.domain.model.BusDriver

class DriversListContract {

    data class DriversListState(
        val drivers: List<BusDriver> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null,
    )

    sealed class DriversListEvent {
        data object GetDrivers : DriversListEvent()
        data object DriversDisplayed : DriversListEvent()
        data object ErrorDisplayed : DriversListEvent()
    }
}