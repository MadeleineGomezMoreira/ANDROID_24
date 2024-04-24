package com.example.bus_api_client_xml.domain.usecases.stops

import com.example.bus_api_client_xml.data.repositories.StopRepository
import javax.inject.Inject

class GetStopsInALineUseCase @Inject constructor(
    private val repository: StopRepository
){
    operator fun invoke(lineId: Int) = repository.getStopsInALine(lineId)
}