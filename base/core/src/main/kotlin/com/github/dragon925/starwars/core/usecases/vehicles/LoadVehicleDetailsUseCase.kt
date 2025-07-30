package com.github.dragon925.starwars.core.usecases.vehicles

import com.github.dragon925.starwars.core.models.Vehicle
import com.github.dragon925.starwars.core.utils.CharacterRepository
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.DetailedResult
import com.github.dragon925.starwars.core.utils.FilmRepository
import com.github.dragon925.starwars.core.utils.VehicleRepository
import com.github.dragon925.starwars.core.utils.anyOf
import com.github.dragon925.starwars.core.utils.firstOf
import com.github.dragon925.starwars.core.utils.flatMapResult
import com.github.dragon925.starwars.core.utils.getIfSuccess
import com.github.dragon925.starwars.core.utils.toDetailedVehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class LoadVehicleDetailsUseCase(
    val vehicleRepository: VehicleRepository,
    val filmRepository: FilmRepository,
    val characterRepository: CharacterRepository
) {

    operator fun invoke(id: Int): Flow<DetailedResult<Vehicle>> = vehicleRepository.loadById(id)
        .flatMapResult { vehicle -> combine(
            characterRepository.loadByIds(vehicle.pilotIds),
            filmRepository.loadByIds(vehicle.filmIds),
            ) { characters, films ->
                val isLoading = anyOf(characters, films) {
                    it is DataResult.Loading
                }

                val errorResult = firstOf(characters, films) {
                    it is DataResult.Error
                } as? DataResult.Error

                return@combine when {
                    isLoading -> DataResult.Loading
                    errorResult != null -> errorResult
                    else -> DataResult.Success(vehicle.toDetailedVehicle(
                        characters = characters.getIfSuccess(),
                        films = films.getIfSuccess()
                    ))
                }
            }
        }
}