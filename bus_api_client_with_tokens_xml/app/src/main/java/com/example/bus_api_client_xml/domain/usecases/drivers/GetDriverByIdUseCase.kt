package com.example.bus_api_client_xml.domain.usecases.drivers

import com.example.bus_api_client_xml.data.repositories.DriverRepository
import javax.inject.Inject

class GetDriverByIdUseCase @Inject constructor(
    private val driverRepository: DriverRepository
) {
    operator fun invoke(id: Int) = driverRepository.getDriverById(id)
}