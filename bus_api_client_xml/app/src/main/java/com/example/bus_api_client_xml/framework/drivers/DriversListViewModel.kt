package com.example.bus_api_client_xml.framework.drivers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_api_client_xml.domain.usecases.drivers.GetAllDriversUseCase
import com.example.bus_api_client_xml.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversListViewModel @Inject constructor(
    private val getAll: GetAllDriversUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<DriversListContract.DriversListState> by lazy {
        MutableStateFlow(DriversListContract.DriversListState())
    }

    val state: StateFlow<DriversListContract.DriversListState> = _state

    fun handleEvent(event: DriversListContract.DriversListEvent) {
        when (event) {
            is DriversListContract.DriversListEvent.GetDrivers -> getAllDrivers()
            is DriversListContract.DriversListEvent.DriversDisplayed -> _state.value =
                _state.value.copy(drivers = emptyList())

            is DriversListContract.DriversListEvent.ErrorDisplayed -> _state.value =
                _state.value.copy(
                    error = null
                )
        }
    }

    private fun getAllDrivers() {
        viewModelScope.launch {
            getAll.invoke().collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            loading = false, error = result.message
                        )
                    }

                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true,
                        )
                    }

                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            loading = false, drivers = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

}