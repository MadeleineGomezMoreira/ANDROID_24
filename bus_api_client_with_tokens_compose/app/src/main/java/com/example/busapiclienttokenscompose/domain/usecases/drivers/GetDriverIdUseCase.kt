package com.example.busapiclienttokenscompose.domain.usecases.drivers

import com.example.busapiclienttokenscompose.data.repositories.DriverRepository
import javax.inject.Inject

class GetDriverIdUseCase @Inject constructor(
    private val driverRepository: DriverRepository
) {
    operator fun invoke(username: String) = driverRepository.getDriverIdByUsername(username)
}