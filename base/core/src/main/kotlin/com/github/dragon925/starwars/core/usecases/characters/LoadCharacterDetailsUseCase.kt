package com.github.dragon925.starwars.core.usecases.characters

import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.utils.CharacterRepository
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.DetailedResult
import com.github.dragon925.starwars.core.utils.FilmRepository
import com.github.dragon925.starwars.core.utils.PlanetRepository
import com.github.dragon925.starwars.core.utils.SpeciesRepository
import com.github.dragon925.starwars.core.utils.StarshipRepository
import com.github.dragon925.starwars.core.utils.VehicleRepository
import com.github.dragon925.starwars.core.utils.anyOf
import com.github.dragon925.starwars.core.utils.firstOf
import com.github.dragon925.starwars.core.utils.flatMapResult
import com.github.dragon925.starwars.core.utils.getIfSuccess
import com.github.dragon925.starwars.core.utils.toDetailedCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class LoadCharacterDetailsUseCase(
    val characterRepository: CharacterRepository,
    val filmRepository: FilmRepository,
    val speciesRepository: SpeciesRepository,
    val planetRepository: PlanetRepository,
    val starshipRepository: StarshipRepository,
    val vehicleRepository: VehicleRepository,
) {

    operator fun invoke(id: Int): Flow<DetailedResult<Character>> = characterRepository.loadById(id)
        .flatMapResult { character -> combine(
                filmRepository.loadByIds(character.filmIds),
                speciesRepository.loadByIds(character.speciesIds),
                planetRepository.loadById(character.homeworldId),
                starshipRepository.loadByIds(character.starshipIds),
                vehicleRepository.loadByIds(character.vehicleIds)
            ) { films, species, homeworld, starships, vehicles ->
                val isLoading = anyOf(films, species, homeworld, starships, vehicles) {
                    it is DataResult.Loading
                }

                val errorResult = firstOf(films, species, homeworld, starships, vehicles) {
                    it is DataResult.Error
                } as? DataResult.Error

                return@combine when {
                    isLoading -> DataResult.Loading
                    errorResult != null -> errorResult
                    else -> DataResult.Success(character.toDetailedCharacter(
                        homeworld = homeworld.getIfSuccess(),
                        films = films.getIfSuccess(),
                        species = species.getIfSuccess(),
                        starships = starships.getIfSuccess(),
                        vehicles = vehicles.getIfSuccess()
                    ))
                }
            }
        }
}