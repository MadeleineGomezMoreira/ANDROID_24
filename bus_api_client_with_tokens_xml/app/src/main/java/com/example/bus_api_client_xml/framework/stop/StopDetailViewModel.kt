package com.example.bus_api_client_xml.framework.stop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_api_client_xml.domain.usecases.lines.GetLinesInAStopUseCase
import com.example.bus_api_client_xml.domain.usecases.stops.GetStopByIdUseCase
import com.example.bus_api_client_xml.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopDetailViewModel @Inject constructor(
    private val getStopById: GetStopByIdUseCase,
    private val getLinesByStop: GetLinesInAStopUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<StopDetailContract.StopDetailState> by lazy {
        MutableStateFlow(StopDetailContract.StopDetailState())
    }

    val state: StateFlow<StopDetailContract.StopDetailState> = _state

    fun handleEvent(event: StopDetailContract.StopDetailEvent) {
        when (event) {
            is StopDetailContract.StopDetailEvent.GetStop -> {
                getStop(event.stopId)
            }

            is StopDetailContract.StopDetailEvent.GetStopLines -> {
                getStopLines(event.stopId)
            }

            StopDetailContract.StopDetailEvent.LinesDisplayed -> _state.value =
                _state.value.copy(lines = emptyList())

            StopDetailContract.StopDetailEvent.ErrorDisplayed -> _state.value =
                _state.value.copy(error = null)
        }
    }

    private fun getStop(stopId: Int) {
        viewModelScope.launch {
            getStopById.invoke(stopId)
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
                                busStop = result.data
                                    ?: StopDetailContract.StopDetailState().busStop
                            )
                        }
                    }
                }
        }
    }

    private fun getStopLines(stopId: Int) {
        viewModelScope.launch {
            getLinesByStop.invoke(stopId)
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
                                lines = result.data ?: StopDetailContract.StopDetailState().lines
                            )
                        }
                    }
                }
        }
    }


}