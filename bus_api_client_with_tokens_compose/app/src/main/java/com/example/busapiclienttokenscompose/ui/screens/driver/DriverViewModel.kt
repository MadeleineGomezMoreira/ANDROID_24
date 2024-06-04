package com.example.busapiclienttokenscompose.ui.screens.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_api_client_xml.utils.NetworkResult
import com.example.busapiclienttokenscompose.domain.usecases.drivers.GetDriverByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverViewModel @Inject constructor(
    private val getDriver: GetDriverByIdUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<DriverContract.DriverState> by lazy {
        MutableStateFlow(DriverContract.DriverState())
    }

    val state: StateFlow<DriverContract.DriverState> = _state.asStateFlow()

    fun handleEvent(event: DriverContract.DriverEvent) {
        when (event) {
            is DriverContract.DriverEvent.GetDriver -> getDriverById(event.driverId)
            is DriverContract.DriverEvent.ErrorDisplayed -> _state.value =
                _state.value.copy(error = null)

            is DriverContract.DriverEvent.LineClicked -> _state.value =
                _state.value.copy(lineId = null)
        }
    }

    private fun getDriverById(driverId: Int) {
        viewModelScope.launch {
            getDriver.invoke(driverId)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _state.value = _state.value.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }

                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true
                            )
                        }

                        is NetworkResult.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                driver = result.data,
//                                lineId = result.data?.line?.id
                            )
                        }
                    }
                }
        }
    }

}