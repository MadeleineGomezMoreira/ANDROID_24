package com.example.busapiclienttokenscompose.domain.usecases.lines

import com.example.busapiclienttokenscompose.data.repositories.LineRepository
import javax.inject.Inject

class GetLinesInAStopUseCase @Inject constructor(
    private val repository: LineRepository
){
    operator fun invoke(stopId: Int) = repository.getLinesInAStop(stopId)
}