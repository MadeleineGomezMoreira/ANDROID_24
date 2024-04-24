package com.example.bus_api_client_xml.domain.usecases.lines

import com.example.bus_api_client_xml.data.repositories.LineRepository
import javax.inject.Inject

class GetLineByIdUseCase @Inject constructor(
    private val repository: LineRepository
){
    operator fun invoke(id: Int) = repository.getLineById(id)
}