package com.example.busapiclienttokenscompose.domain.usecases.stops

import com.example.busapiclienttokenscompose.data.repositories.StopRepository
import javax.inject.Inject

class GetStopsInALineUseCase @Inject constructor(
    private val repository: StopRepository
){
    operator fun invoke(lineId: Int) = repository.getStopsInALine(lineId)
}