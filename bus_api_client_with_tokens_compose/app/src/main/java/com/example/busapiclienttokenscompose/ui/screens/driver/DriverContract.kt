package com.example.busapiclienttokenscompose.ui.screens.driver

import com.example.busapiclienttokenscompose.domain.model.BusDriver

class DriverContract {

    //TODO: add assigned line to the driver so we can get the lineId from there

    data class DriverState(
        val isLoading: Boolean = false,
        val driver: BusDriver? = null,
        val lineId: Int? = null,
        val error: String? = null,
    )

    sealed class DriverEvent {
        class GetDriver(val driverId: Int) : DriverEvent()
        data object ErrorDisplayed : DriverEvent()
        data object LineClicked : DriverEvent()
    }

}