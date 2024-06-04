package com.example.busapiclienttokenscompose.domain.usecases.lines

import com.example.busapiclienttokenscompose.data.repositories.LineRepository
import javax.inject.Inject

class GetLineByIdUseCase @Inject constructor(
    private val repository: LineRepository
){
    operator fun invoke(id: Int) = repository.getLineById(id)
}