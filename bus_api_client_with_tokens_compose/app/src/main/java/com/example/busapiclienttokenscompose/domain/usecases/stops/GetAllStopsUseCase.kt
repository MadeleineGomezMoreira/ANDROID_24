package com.example.busapiclienttokenscompose.domain.usecases.stops

import com.example.busapiclienttokenscompose.data.repositories.StopRepository
import javax.inject.Inject

class GetAllStopsUseCase @Inject constructor(
    private val repository: StopRepository
){
    operator fun invoke() = repository.getAllStops()
}