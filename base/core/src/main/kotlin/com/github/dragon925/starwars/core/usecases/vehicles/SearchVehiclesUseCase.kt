package com.github.dragon925.starwars.core.usecases.vehicles

import com.github.dragon925.starwars.core.models.Vehicle
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.VehicleRepository
import kotlinx.coroutines.flow.Flow

class SearchVehiclesUseCase(
    val vehicleRepository: VehicleRepository
) {

    operator fun invoke(
        query: String,
        page: Int = 1
    ): Flow<PageResult<Vehicle>> = vehicleRepository.search(query, page)
}