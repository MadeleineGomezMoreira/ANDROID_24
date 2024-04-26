package com.example.bus_api_client_xml.framework.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_api_client_xml.domain.usecases.drivers.GetDriverByIdUseCase
import com.example.bus_api_client_xml.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverDetailViewModel @Inject constructor(
    private val get: GetDriverByIdUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<DriverDetailContract.DriverDetailState> by lazy {
        MutableStateFlow(DriverDetailContract.DriverDetailState())
    }

    val state: StateFlow<DriverDetailContract.DriverDetailState> = _state

    fun handleEvent(event: DriverDetailContract.DriverDetailEvent) {
        when (event) {
            is DriverDetailContract.DriverDetailEvent.GetDriver -> {
                getDriver(event.driverId)
            }
            DriverDetailContract.DriverDetailEvent.ErrorDisplayed -> _state.value =
                _state.value.copy(error = null)
        }
    }

    private fun getDriver(driverId: Int) {
        viewModelScope.launch {
            get.invoke(driverId)
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
                                loading = true
                            )
                        }
                        is NetworkResult.Success -> {
                            _state.value = _state.value.copy(
                                loading = false,
                                busDriver = result.data ?: DriverDetailContract.DriverDetailState().busDriver,
                            )
                        }
                    }
                }
        }
    }


}