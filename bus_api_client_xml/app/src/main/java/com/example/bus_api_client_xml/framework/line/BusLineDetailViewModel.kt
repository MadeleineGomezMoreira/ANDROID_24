package com.example.bus_api_client_xml.framework.line

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_api_client_xml.domain.usecases.lines.GetLineByIdUseCase
import com.example.bus_api_client_xml.domain.usecases.stops.GetStopsInALineUseCase
import com.example.bus_api_client_xml.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusLineDetailViewModel @Inject constructor(
    private val getBusLine: GetLineByIdUseCase,
    private val getBusLineStops: GetStopsInALineUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<BusLineDetailContract.BusLineDetailState> by lazy {
        MutableStateFlow(BusLineDetailContract.BusLineDetailState())
    }

    val state: StateFlow<BusLineDetailContract.BusLineDetailState> = _state

    fun handleEvent(event: BusLineDetailContract.BusLineDetailEvent) {
        when (event) {
            is BusLineDetailContract.BusLineDetailEvent.GetBusLine -> {
                getBusLine(event.lineId)
            }

            is BusLineDetailContract.BusLineDetailEvent.GetBusLineStops -> {
                getBusLineStops(event.lineId)
            }

            BusLineDetailContract.BusLineDetailEvent.StopsDisplayed -> _state.value =
                _state.value.copy(stops = emptyList())

            BusLineDetailContract.BusLineDetailEvent.ErrorDisplayed -> _state.value =
                _state.value.copy(error = null)
        }
    }

    private fun getBusLine(busLineId: Int) {
        viewModelScope.launch {
            getBusLine.invoke(busLineId)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _state.value = _state.value.copy(
                                loading = false,
                                error = result.message
                            )
                        }

                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(
                                loading = true,
                            )
                        }

                        is NetworkResult.Success -> {
                            _state.value = _state.value.copy(
                                loading = false,
                                busLine = result.data
                                    ?: BusLineDetailContract.BusLineDetailState().busLine,
                            )
                        }
                    }
                }
        }
    }

    private fun getBusLineStops(busLineId: Int) {
        viewModelScope.launch {
            getBusLineStops.invoke(busLineId)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _state.value = _state.value.copy(
                                loading = false,
                                error = result.message
                            )
                        }

                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(
                                loading = true,
                            )
                        }

                        is NetworkResult.Success -> {
                            _state.value = _state.value.copy(
                                loading = false,
                                stops = result.data
                                    ?: BusLineDetailContract.BusLineDetailState().stops,
                            )
                        }
                    }
                }
        }
    }
}

