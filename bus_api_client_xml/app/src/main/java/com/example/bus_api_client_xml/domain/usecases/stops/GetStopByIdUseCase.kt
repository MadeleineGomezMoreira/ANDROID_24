package com.example.bus_api_client_xml.domain.usecases.stops

import com.example.bus_api_client_xml.data.repositories.StopRepository
import javax.inject.Inject

class GetStopByIdUseCase @Inject constructor(
    private val repository: StopRepository
) {
    operator fun invoke(stopId: Int) = repository.getStopById(stopId)
}