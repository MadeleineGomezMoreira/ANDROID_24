package com.example.busapiclienttokenscompose.domain.usecases.lines

import com.example.busapiclienttokenscompose.data.repositories.LineRepository
import javax.inject.Inject

class GetAllLinesUseCase @Inject constructor(
    private val repository: LineRepository
){
    operator fun invoke() = repository.getAllLines()
}